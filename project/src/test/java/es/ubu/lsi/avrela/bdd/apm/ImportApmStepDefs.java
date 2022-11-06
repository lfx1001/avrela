package es.ubu.lsi.avrela.bdd.apm;

import es.ubu.lsi.avrela.apm.port.SprintFinder;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ImportApmStepDefs {

  String repositoryOwner = null, repositoryName = null;

  ZonedDateTime  beginAt = null, endAt = null;

  SprintFinder sprintFinder = null;

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

  }

  @Then("agile project management should match expected")
  public void agileProjectManagementInfoShouldBeRetrieved() {
  }
}
