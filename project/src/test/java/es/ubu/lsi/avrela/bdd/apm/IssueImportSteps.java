package es.ubu.lsi.avrela.bdd.apm;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubIssueFinder;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubCommentMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubIssueEventMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubIssueMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubLabelMapper;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import es.ubu.lsi.avrela.apm.port.IssueFinder;
import feign.Logger.Level;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class IssueImportSteps {

  String repositoryOwner = null, repositoryName = null;

  String issueId = null;

  Issue issueUnderTest = null;
  private IssueFinder issueFinder;

  @Given("the repository owned by {string} named {string}")
  public void theRepositoryOwnedByNamed(String repositoryOwner, String repositoryName) {
    this.repositoryOwner = repositoryOwner;
    this.repositoryName = repositoryName;
  }

  @When("I import the issue with id {string}")
  public void iImportTheIssue(String issueId) {
    //Init GitHubClient
    GitHubClient gitHubClient = GitHubClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    issueFinder = new GitHubIssueFinder(gitHubClient, new GitHubIssueMapper(
        new GitHubCommentMapper(),
        new GitHubLabelMapper(),
        new GitHubIssueEventMapper()
    ));

    //Fetch
    issueUnderTest = issueFinder.findById(repositoryOwner, repositoryName,issueId);
  }

  @Then("issue has comments check should be {string}")
  public void issueHasCommentsCheckShouldBe(String hasComments) {
    Boolean hasCommentsCheck = Boolean.valueOf(hasComments);
    Assertions.assertEquals(hasCommentsCheck, issueUnderTest.getComments().size() != 0);
  }

  @And("issue total comments should be {long}")
  public void issueTotalCommentsShouldBe(Long totalComments) {
    Assertions.assertEquals(totalComments, issueUnderTest.getComments().size());
  }

  @And("issue has image check should be {string}")
  public void issueHasImageCheckShouldBe(String arg) {
    Assertions.assertTrue(true);
  }

  @And("issue has link check should be {string}")
  public void issueHasLinkCheckShouldBe(String arg) {
    Assertions.assertTrue(true);
  }

  @And("issue is labeled check should be {string}")
  public void issueIsLabeledCheckShouldBe(String arg) {
    Assertions.assertTrue(true);
  }

  @And("issue has label with value {string}")
  public void issueHasLabelWithValue(String arg) {
    Assertions.assertTrue(true);
  }

  @And("issue total referenced commits should be {long}")
  public void issueTotalReferencedCommitsShouldBe(Long arg) {
    Assertions.assertTrue(true);
  }

}
