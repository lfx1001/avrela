package es.ubu.lsi.avrela.bdd.apm;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;

public class ApmCaseStudySimulationEvaluationSteps {

  @Given("a case study with repo owner {string}, name {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aCaseStudyWithRepoOwnerNameAndTimePeriod(String arg0, String arg1, ZonedDateTime arg2,
      ZonedDateTime arg3) {
    //throw new io.cucumber.java.PendingException();
  }

  @And("a simulation with repo owner {string}, name {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aSimulationWithRepoOwnerNameAndTimePeriod(String arg0, String arg1, ZonedDateTime arg2,
      ZonedDateTime arg3) {
    //throw new io.cucumber.java.PendingException();
  }

  @And("a rubric")
  public void aRubric(DataTable dataTable) {
    //throw new io.cucumber.java.PendingException();
  }

  @When("I apply the rubric")
  public void iApplyTheRubric() {
    //throw new io.cucumber.java.PendingException();
  }

  @Then("rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
    //throw new io.cucumber.java.PendingException();
  }
}
