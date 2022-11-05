package es.ubu.lsi.avrela.bdd.apm;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ImportApmStepDefs {

  @ParameterType(".*")
  public ZonedDateTime zoneddatetime(String zonedDateTime) {
    return ZonedDateTime.parse(zonedDateTime);
  }

  @Given("a repository owned by {string} named {string}")
  public void aRepositoryOwnedByNamed(String arg0, String arg1) {
    log.debug("repository is [{}]", arg0);
  }

  @And("the dates {zoneddatetime} and {zoneddatetime}")
  public void theDatesAnd(ZonedDateTime beginAt, ZonedDateTime endAt) {
  }

  @When("I try to import the repository")
  public void iTryToImportTheRepository() {
  }

  @Then("agile project management info should be retrieved")
  public void agileProjectManagementInfoShouldBeRetrieved() {
  }
}
