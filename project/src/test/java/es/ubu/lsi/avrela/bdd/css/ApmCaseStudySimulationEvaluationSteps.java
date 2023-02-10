package es.ubu.lsi.avrela.bdd.css;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubHistoricalApmDataRepository;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubSprintRepository;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.domain.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import es.ubu.lsi.avrela.css.domain.model.ApmCaseStudySimulation;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class ApmCaseStudySimulationEvaluationSteps {

  GitHubHistoricalApmDataRepository apmDataRepository;
  HistoricalApmData caseStudy;
  HistoricalApmData simulation;
  ApmCaseStudySimulation apmCaseStudySimulation;

  Integer simulationParticipants;
  List<Double> teamWorkCriteriaScale;
  private Integer actualTeamWorkRubricValue = 0;


  List<Double> toolLearningDescriptionCriteriaScale;
  private Integer actualToolLearningDescriptionRubricValue = 0;

  @Given("a case study with repo owner {string}, name {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aCaseStudyWithRepoOwnerNameAndTimePeriod(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    //Init GitHubClient
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    GitHubSprintRepository sprintFinder = new GitHubSprintRepository(gitHubApmClient, milestoneMapper);
    apmDataRepository = new GitHubHistoricalApmDataRepository(sprintFinder);
    //Uncomment lines below for case study evaluation
    caseStudy = apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(repoOwner, repoName, startAt, endAt);
  }

  @And("a simulation with repo owner {string}, name {string}, time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aSimulationWithRepoOwnerNameAndTimePeriod(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt, Integer simulationParticipants) {
    this.simulationParticipants = simulationParticipants;
    simulation = apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(repoOwner, repoName, startAt, endAt);

    apmCaseStudySimulation = ApmCaseStudySimulation.builder()
        .caseStudy(caseStudy)
        .simulation(simulation)
        .build();
  }

  @And("a rubric")
  public void aRubric(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Obtain teamwork criteria  scale
    teamWorkCriteriaScale = Rubric.toCriteriaScale(rows.get(0));

    //Obtain TaskManagement Tool Learning - Description criteria scale
    toolLearningDescriptionCriteriaScale = Rubric.toCriteriaScale(rows.get(1));

  }
  @When("I apply the rubric")
  public void iApplyTheRubric() {
    //Evaluate Teamwork criteria
    Double teamWorkDividend = 100d *  simulation.filterIssues(
        Issue.participantsGreaterThanOrEqual(simulationParticipants)).size();
    Double teamWordDivisor = simulation.countIssues().doubleValue();
    Double teamWork = teamWorkDividend / teamWordDivisor;
    actualTeamWorkRubricValue = Rubric.evaluateCriteria(teamWorkCriteriaScale, teamWork);

    //Evaluate TaskManagement Tool Learning - Description criteria
    Double toolLearningDescriptionDividend = 100d * apmCaseStudySimulation.filterIssueMatchComparisons(
        null).size();
    Double toolLearningDescriptionDivisor = simulation.countIssues().doubleValue();
    Double toolLearningDescription = toolLearningDescriptionDividend / toolLearningDescriptionDivisor;
    actualToolLearningDescriptionRubricValue = Rubric.evaluateCriteria(toolLearningDescriptionCriteriaScale, toolLearningDescription);
    log.debug("toolLearningDescriptionDividend [{}] , toolLearningDescriptionDivisor is [{}]", toolLearningDescriptionDividend, toolLearningDescriptionDivisor);

    log.debug("Calculated pos for [{}] value is [{}]", teamWork, actualTeamWorkRubricValue);
  }

  @Then("rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Process team work criteria
    Integer expectedTeamWorkRubricValue = Rubric.getExpectedRubricValue(rows.get(0));

    Assertions.assertEquals(expectedTeamWorkRubricValue, actualTeamWorkRubricValue, "Teamwork rubric evaluation score mismatch");

    //Process tool learning description criteria
    Integer expectedToolLearningDescriptionRubricValue = Rubric.getExpectedRubricValue(rows.get(1));

    Assertions.assertEquals(expectedToolLearningDescriptionRubricValue , actualToolLearningDescriptionRubricValue, "Tool learning description rubric evaluation score mismatch");
  }


}
