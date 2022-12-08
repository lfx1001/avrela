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
    //Process team work criteria
    Map<String, String>  teamWorkCriteriaRow = rows.get(0);
    String[] ratingScaleValues = {"0", "1", "2"};
    List<Double> teamWorkCriteriaScale = new ArrayList<>();
    for(String ratingScaleValue: ratingScaleValues){
      if (!"None".equals(teamWorkCriteriaRow.get(ratingScaleValue))){
        teamWorkCriteriaScale.add(Double.parseDouble(teamWorkCriteriaRow.get(ratingScaleValue)));
      } else {
        teamWorkCriteriaScale.add(Double.MIN_VALUE);
      }
    }
    //Evaluate value
    //1.Get value
    Double teamWorkNum = 100d *  simulation.filterIssues(
        Issue.participantsGreaterThanOrEqual(simulationParticipants)).size();
    Double teamWordDen = simulation.countIssues().doubleValue();
    Double teamWork = teamWorkNum / teamWordDen;
    Integer scaleValueCurrent = 0;
    Boolean finish = false;
    //2. Get scale value
    while(!finish){
      if(teamWork >= teamWorkCriteriaScale.get(scaleValueCurrent)){
        if(Double.MIN_VALUE != teamWorkCriteriaScale.get(scaleValueCurrent)){
          actualTeamWorkRubricValue = scaleValueCurrent;
          if (scaleValueCurrent == teamWorkCriteriaScale.size()-1 ){
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
    log.debug("Calculated pos for [{}] value is [{}]", teamWork, actualTeamWorkRubricValue);

  }

  @When("I apply the rubric")
  public void iApplyTheRubric() {
    //throw new io.cucumber.java.PendingException();
  }

  @Then("rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    //Process team work criteria
    Map<String, String>  teamWorkEvaluationRow = rows.get(0);
    String[] ratingScaleValues = {"0", "1", "2"};
    Integer expectedTeamWorkRubricValue = 0;
    for(String ratingScaleValue: ratingScaleValues){
      if ("X".equals(teamWorkEvaluationRow.get(ratingScaleValue))){
        break;
      }
      expectedTeamWorkRubricValue++;
    }

    Assertions.assertEquals(expectedTeamWorkRubricValue, actualTeamWorkRubricValue);
  }

}
