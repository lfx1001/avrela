package es.ubu.lsi.avrela.bdd.apm;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubHistoricalApmDataRepository;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubSprintFinder;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.domain.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class ApmCaseStudySimulationEvaluationSteps {

  HistoricalApmData caseStudy;

  HistoricalApmData simulation;

  Integer simulationParticipants;

  List<Double> teamWorkCriteriaScale;
  GitHubHistoricalApmDataRepository apmDataRepository;


  private Integer actualTeamWorkRubricValue = 0;

  @Given("a case study with repo owner {string}, name {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aCaseStudyWithRepoOwnerNameAndTimePeriod(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    //Init GitHubClient
    GitHubClient gitHubClient = GitHubClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    GitHubSprintFinder sprintFinder = new GitHubSprintFinder(gitHubClient, milestoneMapper);
    apmDataRepository = new GitHubHistoricalApmDataRepository(sprintFinder);
    //Uncomment lines below for case study evaluation
    //Fetch
    // caseStudy = apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(repoOwner, repoName, startAt, endAt);
  }

  @And("a simulation with repo owner {string}, name {string}, time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aSimulationWithRepoOwnerNameAndTimePeriod(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt, Integer simulationParticipants) {
    this.simulationParticipants = simulationParticipants;
    simulation = apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(repoOwner, repoName, startAt, endAt);
  }

  @And("a rubric")
  public void aRubric(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Obtain criteria scale
    Map<String, String>  teamWorkCriteriaRow = rows.get(0);
    teamWorkCriteriaScale = toCriteriaScale(teamWorkCriteriaRow);

  }
  @When("I apply the rubric")
  public void iApplyTheRubric() {
    //Evaluate value
    Double teamWorkDividend = 100d *  simulation.filterIssues(
        Issue.participantsGreaterThanOrEqual(simulationParticipants)).size();
    Double teamWordDivisor = simulation.countIssues().doubleValue();
    Double teamWork = teamWorkDividend / teamWordDivisor;
    actualTeamWorkRubricValue = evaluateCriteria(teamWorkCriteriaScale, teamWork);

    log.debug("Calculated pos for [{}] value is [{}]", teamWork, actualTeamWorkRubricValue);
  }

  @Then("rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Process team work criteria
    Map<String, String>  teamWorkEvaluationRow = rows.get(0);
    Integer expectedTeamWorkRubricValue = getExpectedRubricValue(teamWorkEvaluationRow);

    Assertions.assertEquals(expectedTeamWorkRubricValue, actualTeamWorkRubricValue);
  }

  private static Integer getExpectedRubricValue(Map<String, String> teamWorkEvaluationRow) {
    String[] ratingScaleValues = {"0", "1", "2"};
    Integer expectedTeamWorkRubricValue = 0;
    for(String ratingScaleValue: ratingScaleValues){
      if ("X".equals(teamWorkEvaluationRow.get(ratingScaleValue))){
        break;
      }
      expectedTeamWorkRubricValue++;
    }
    return expectedTeamWorkRubricValue;
  }

  private List<Double> toCriteriaScale(Map<String, String> dataTableRow) {
    final String[] ratingScaleValues = {"0", "1", "2"};
    List<Double> result = new ArrayList<>();
    for(String ratingScaleValue: ratingScaleValues){
      if (!"None".equals(dataTableRow.get(ratingScaleValue))){
        result.add(Double.parseDouble(dataTableRow.get(ratingScaleValue)));
      } else {
        result.add(Double.MIN_VALUE);
      }
    }
    return result;
  }

  /**
   *
   * @param criteriaScale
   * @param criteriaValue
   * @return evaluation as criteria scale position.
   */
  private Integer evaluateCriteria(List<Double> criteriaScale, Double criteriaValue) {
    Integer result = 0;
    Integer scaleValueCurrent = 0;
    Boolean finish = false;
    //Get scale value
    while(!finish){
      if(criteriaValue >= criteriaScale.get(scaleValueCurrent)){
        if(Double.MIN_VALUE != criteriaScale.get(scaleValueCurrent)){
          result = scaleValueCurrent;
          if (scaleValueCurrent == criteriaScale.size()-1 ){
            finish = true;
          }else{
            scaleValueCurrent++;
          }
        }else{//None value detected
          //None values at the end are not supported
          scaleValueCurrent++;
        }
      }else{
        finish = true;
      }
    }
    return result;
  }


}
