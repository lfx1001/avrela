package es.ubu.lsi.avrela.bdd.css;

import es.ubu.lsi.avrela.apm.model.IssueSimilarity;
import es.ubu.lsi.avrela.css.model.ScmCaseStudySimulation;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubHistoricalScmDataRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.model.CommitSimilarity;
import es.ubu.lsi.avrela.scm.model.CommitSimilarity.Feature;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;


@Slf4j
public class ScmCaseStudySimulationEvaluationSteps {

  GitHubHistoricalScmDataRepository scmHistoricalDataRepository;

  HistoricalScmData caseStudy;

  HistoricalScmData simulation;

  Integer simulationParticipants;

  List<Double> teamWorkCriteriaScale;

  List<Double> commitSimilarityCriteriaScale;
  private int commitSimilarityThreshold;

  private ScmCaseStudySimulation scmCaseStudySimulation;

  private EnumMap<Feature, Double> featureWeights;

  @Given("a scm case study with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aScmCaseStudyWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt) {
    // Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.FULL);

    GitHubCommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, new GitHubCommitMapper(new GitHubCommitFileMapper()));

    scmHistoricalDataRepository = new GitHubHistoricalScmDataRepository(commitRepository);

    caseStudy = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(repoOwner, repoName, branch, startAt, endAt);

    //TODO temporary use case study as validation
    simulation = caseStudy;

    this.scmCaseStudySimulation = ScmCaseStudySimulation.builder()
        .caseStudy(this.caseStudy)
        .simulation(this.simulation)
        .build();

  }

  @And("a scm simulation with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aScmSimulationWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt, Integer simulationParticipants) {
    this.simulationParticipants = simulationParticipants;
  }

  @And("commit similarity function weights are \\(commit files = {int} , commit name = {int})")
  public void commitSimilarityFunctionWeightsAreCommitFilesCommitName(int commitFilesWeight, int commitNameWeight) {
    this.featureWeights = new EnumMap<>(CommitSimilarity.Feature.class);
    this.featureWeights.put(CommitSimilarity.Feature.FILES, (double) (commitFilesWeight/100));
    this.featureWeights.put(CommitSimilarity.Feature.MESSAGE, (double) (commitNameWeight/100));
  }

  @And("commit similarity threshold is set at {int}")
  public void commitSimilarityThresholdIsSetAt(int commitSimilarityThreshold) {
    this.commitSimilarityThreshold = commitSimilarityThreshold;
  }

  @And("a SCM evaluation rubric")
  public void aRubric(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Obtain teamwork criteria  scale
    teamWorkCriteriaScale = Rubric.toCriteriaScale(rows.get(0));

    commitSimilarityCriteriaScale = Rubric.toCriteriaScale(rows.get(1));


  }

  @When("I apply the SCM evaluation rubric")
  public void iApplyTheRubric() {
    //Evaluate Teamwork criteria



  }

  @Then("SCM evaluation rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Evaluate Teamwork: criteria won't apply as it is defined.
    //TODO: redefine as a numeric measure in order to consider evaluation scale
    Integer teamworkGrade = teamWorkEvaluation(simulationParticipants, simulation);
    Assertions.assertEquals(2, teamworkGrade, "Teamwork grade");

    //Evaluate commit similarity
    Double commitSimilarityDividend = 100d * scmCaseStudySimulation.filterCommitMatchComparison(this.featureWeights, this.commitSimilarityThreshold).size();
    Double commitSimilarityDivisor = Double.valueOf(simulation.getCommits().size());
    Double commitSimilarity = commitSimilarityDividend / commitSimilarityDivisor;
    Integer actualRubricValue = Rubric.evaluateCriteria(commitSimilarityCriteriaScale, commitSimilarity);
    Integer expectedRubricValue = Rubric.getExpectedRubricValue(rows.get(1));

    Assertions.assertEquals(expectedRubricValue, actualRubricValue);
  }

  private Integer teamWorkEvaluation(Integer simulationParticipants, HistoricalScmData simulation) {
    Set<String> participants = simulation.getParticipants();
    log.debug("Total participants [{}]", participants.size());
    //Evaluate agile fidelity (authors alternate commits)
    if (simulationParticipants.equals(simulation.getParticipants().size())){
      if (simulation.alternativeCommits(simulationParticipants)){
        return 2;
      } else {
        return 1;
      }
    }else {
      return 0;
    }
  }



}
