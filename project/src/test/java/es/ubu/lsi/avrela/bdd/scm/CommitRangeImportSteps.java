package es.ubu.lsi.avrela.bdd.scm;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;

public class CommitRangeImportSteps {

  @Given("the commit range belongs to the repository owned by {string} named {string}")
  public void theCommitRangeBelongsToTheRepositoryOwnedByNamed(String repoOwner, String repoName) {
    throw new io.cucumber.java.PendingException();
  }

  @And("branch is {string}")
  public void branchIs(String arg0) {
    throw new io.cucumber.java.PendingException();
  }

  @And("range is {zoneddatetime} {zoneddatetime}")
  public void andRangeIs(ZonedDateTime startAt, ZonedDateTime endAt) {
    throw new io.cucumber.java.PendingException();
  }

  @When("I import the commit")
  public void iImportTheCommit() {
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following commits should be present in same order")
  public void theFollowingCommitsShouldBePresentInSameOrder(DataTable dataTable) {
    throw new io.cucumber.java.PendingException();
  }
}
