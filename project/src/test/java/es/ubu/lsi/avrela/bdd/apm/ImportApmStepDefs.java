package es.ubu.lsi.avrela.bdd.apm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubSprintFinder;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.domain.model.IssueState;
import es.ubu.lsi.avrela.apm.domain.model.Sprint;
import es.ubu.lsi.avrela.apm.port.SprintFinder;
import feign.Logger.Level;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ImportApmStepDefs {

  String repositoryOwner = null, repositoryName = null;

  ZonedDateTime  beginAt = null, endAt = null;

  SprintFinder sprintFinder = null;

  List<Sprint> sprints = null;

  @ParameterType(".*")
  public ZonedDateTime zoneddatetime(String zonedDateTime) {
    return ZonedDateTime.parse(zonedDateTime);
  }

  @Given("a repository owned by {string} named {string}")
  public void aRepositoryOwnedByNamed(String repositoryOwner, String repositoryName) {
    this.repositoryOwner = repositoryOwner;
    this.repositoryName = repositoryName;
  }

  @And("the dates {zoneddatetime} and {zoneddatetime}")
  public void theDatesAnd(ZonedDateTime beginAt, ZonedDateTime endAt) {
    this.beginAt = beginAt;
    this.endAt = endAt;
  }

  @When("I import the agile project management info")
  public void iTryToImportTheRepository() {
    //Init GitHubClient
    GitHubClient gitHubClient = GitHubClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    this.sprintFinder = new GitHubSprintFinder(gitHubClient, milestoneMapper);
    //Fetch
    this.sprints = this.sprintFinder.findByDueOnBetween(this.repositoryOwner, this.repositoryName, this.beginAt, this.endAt);
  }

  @Then("agile project management should match expected")
  public void agileProjectManagementInfoShouldBeRetrieved() {
    assertNotNull(this.sprints);
    Sprint sprint = this.sprints.get(0);

    assertEquals(17L, sprint.countIssues());
    assertEquals(7L , sprint.countIssuesByLabel("documentation"));
    assertEquals(4L , sprint.countIssuesByLabel("feature"));
    assertEquals(2L , sprint.countIssuesByLabel("testing"));
    assertEquals(4L , sprint.countIssuesByLabel("bug"));
    assertEquals(2L, sprint.countIssuesByHasComments(true));
    assertEquals(17L, sprint.countIssuesByState(IssueState.CLOSED));






  }
}
