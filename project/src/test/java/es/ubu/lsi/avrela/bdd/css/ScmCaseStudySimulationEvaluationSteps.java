package es.ubu.lsi.avrela.bdd.css;

import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubHistoricalScmDataRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.domain.model.HistoricalScmData;
import feign.Logger.Level;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.ZonedDateTime;

public class ScmCaseStudySimulationEvaluationSteps {

  GitHubHistoricalScmDataRepository scmHistoricalDataRepository;

  HistoricalScmData caseStudy;

  HistoricalScmData simulation;

  Integer simulationParticipants;

  @Given("a scm case study with repo owner {string}, name {string}, branch is {string} and time period {zoneddatetime} {zoneddatetime}")
  public void aScmCaseStudyWithRepoOwnerNameBranchIsAndTimePeriod(String repoOwner, String repoName, String branch, ZonedDateTime startAt, ZonedDateTime endAt) {
    // Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.BASIC);

    GitHubCommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, new GitHubCommitMapper(new GitHubCommitFileMapper()));

    scmHistoricalDataRepository = new GitHubHistoricalScmDataRepository(commitRepository);

    caseStudy = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(repoOwner, repoName, branch, startAt, endAt);

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
