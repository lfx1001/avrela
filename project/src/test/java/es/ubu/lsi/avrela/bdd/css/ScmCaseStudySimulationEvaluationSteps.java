package es.ubu.lsi.avrela.bdd.css;

import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubHistoricalScmDataRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.domain.model.HistoricalScmData;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
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

  @Given("a scm case study with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aScmCaseStudyWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt) {
    // Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.FULL);

    GitHubCommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, new GitHubCommitMapper(new GitHubCommitFileMapper()));

    scmHistoricalDataRepository = new GitHubHistoricalScmDataRepository(commitRepository);

    caseStudy = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(repoOwner, repoName, branch, startAt, endAt);

    //TODO temporary use case study as validation
    simulation = caseStudy;

  }

  @And("a scm simulation with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aScmSimulationWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt, Integer simulationParticipants) {
    this.simulationParticipants = simulationParticipants;
  }

  @And("a SCM evaluation rubric")
  public void aRubric(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Obtain teamwork criteria  scale
    teamWorkCriteriaScale = Rubric.toCriteriaScale(rows.get(0));
  }

  @When("I apply the SCM evaluation rubric")
  public void iApplyTheRubric() {
    //Evaluate Teamwork criteria



  }

  @Then("SCM evaluation rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    //Teamwork: criteria won apply

    Integer teamworkGrade = teamWorkEvaluation(simulationParticipants, simulation);

    Assertions.assertEquals(2, teamworkGrade, "Teamwork grade");
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
