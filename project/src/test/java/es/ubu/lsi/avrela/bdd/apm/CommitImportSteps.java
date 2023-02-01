package es.ubu.lsi.avrela.bdd.apm;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommitImportSteps {

  @Given("commits are imported from the repository owned by {string} named {string}")
  public void commitsAreImportedFromTheRepositoryOwnedByNamed(String arg0, String arg1) {
    throw new io.cucumber.java.PendingException();
  }

  @And("a commit with SHA  <sha>")
  public void aCommitWithSHASha() {
    throw new io.cucumber.java.PendingException();
  }

  @When("I import the commit")
  public void iImportTheCommit() {
    throw new io.cucumber.java.PendingException();
  }

  @Then("total files changed should be <totalFilesChanged>")
  public void totalFilesChangedShouldBeTotalFilesChanged() {
    throw new io.cucumber.java.PendingException();
  }

  @And("total additions should be <totalAdditions>")
  public void totalAdditionsShouldBeTotalAdditions() {
    throw new io.cucumber.java.PendingException();
  }

  @And("total deletions should be <totalDeletions>")
  public void totalDeletionsShouldBeTotalDeletions() {
    throw new io.cucumber.java.PendingException();
  }
}
