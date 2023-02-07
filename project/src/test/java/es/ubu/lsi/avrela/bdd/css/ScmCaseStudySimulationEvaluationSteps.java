package es.ubu.lsi.avrela.bdd.css;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;

public class ScmCaseStudySimulationEvaluationSteps {

  @Given("a scm case study with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aScmCaseStudyWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt) {
  }

  @And("a scm simulation with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime} and {int} participant\\(s)")
  public void aScmSimulationWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt, Integer simulationParticipants) {
  }

  @And("a SCM evaluation rubric")
  public void aRubric(DataTable dataTable) {
  }

  @When("I apply the SCM evaluation rubric")
  public void iApplyTheRubric() {
  }

  @Then("SCM evaluation rubric score should be")
  public void rubricScoreShouldBe(DataTable dataTable) {
   }

}
