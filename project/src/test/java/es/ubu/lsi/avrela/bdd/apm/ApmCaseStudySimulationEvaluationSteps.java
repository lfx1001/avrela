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

@Slf4j
public class ApmCaseStudySimulationEvaluationSteps {

  HistoricalApmData caseStudy;

  HistoricalApmData simulation;

  Integer simulationParticipants;

  GitHubHistoricalApmDataRepository apmDataRepository;

  @Given("a case study with repo owner {string}, name {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aCaseStudyWithRepoOwnerNameAndTimePeriod(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    //Init GitHubClient
    GitHubClient gitHubClient = GitHubClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    GitHubSprintFinder sprintFinder = new GitHubSprintFinder(gitHubClient, milestoneMapper);
    apmDataRepository = new GitHubHistoricalApmDataRepository(sprintFinder);
    //Fetch
    caseStudy = apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(repoOwner, repoName, startAt, endAt);
  }

  @And("a simulation with repo owner {string}, name {string}, time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aSimulationWithRepoOwnerNameAndTimePeriod(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt, Integer simulationParticipants) {
    simulationParticipants = simulationParticipants;
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
    Double teamWork = 100D;
    Integer scaleValuePos = 0, scaleValueCurrent = 0;
    Boolean finish = false;
    while(!finish){
      if(teamWork >= teamWorkCriteriaScale.get(scaleValueCurrent)){
        if(Double.MIN_VALUE != teamWorkCriteriaScale.get(scaleValueCurrent)){
          scaleValuePos = scaleValueCurrent;
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
    log.debug("Calculated pos for [{}] value is [{}]", teamWork, scaleValuePos);

  }

  @When("I apply the rubric")
  public void iApplyTheRubric() {
    //throw new io.cucumber.java.PendingException();
    simulation.getParticipants().forEach(
        user -> log.debug("User [{}]", user)
    );
    log.debug("Number of issues is [{}]", simulation.countIssues());
    log.debug("Number of issues that match predicate  [{}]", simulation.filterIssues(
        Issue.participantsGreaterThanOrEqual(1)).size());
  }

  @Then("rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    //throw new io.cucumber.java.PendingException();
  }

}
