package es.ubu.lsi.avrela.bdd.scm;

import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.domain.model.Commit;
import es.ubu.lsi.avrela.scm.port.CommitRepository;
import feign.Logger.Level;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CommitImportSteps {

  private String repoOwner = null;

  private String repoName = null;

  private String commitSha = null;

  private Commit commit;

  @Given("commits are imported from the repository owned by {string} named {string}")
  public void commitsAreImportedFromTheRepositoryOwnedByNamed(String repoOwner, String repoName) {
    this.repoOwner = this.repoOwner;
    this.repoName = this.repoName;
  }

  @And("a commit with SHA {string}")
  public void aCommitWithSHA(String commitSha) {
    this.commitSha = commitSha;
  }

  @When("I import the commit")
  public void iImportTheCommit() {
    //Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.BASIC);
    GitHubCommitMapper commitMapper = new GitHubCommitMapper( new GitHubCommitFileMapper());
    CommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, commitMapper);
  }

  @Then("total files changed should be {long}")
  public void totalFilesChangedShouldBeTotalFilesChanged(Long totalFileChanged) {
  }

  @And("total additions should be {long}")
  public void totalAdditionsShouldBeTotalAdditions(Long totalAdditions) {
  }

  @And("total deletions should be {long}")
  public void totalDeletionsShouldBeTotalDeletions(Long totalDeletions) {
  }
}
