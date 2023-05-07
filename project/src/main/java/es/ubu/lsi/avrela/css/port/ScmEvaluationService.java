package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.css.adapter.web.WebHistoricalScmData;
import es.ubu.lsi.avrela.css.adapter.web.WebScmCaseStudySimulation;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubCommitRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubHistoricalScmDataRepository;
import es.ubu.lsi.avrela.scm.adapter.github.GitHubScmClient;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitFileMapper;
import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import feign.Logger.Level;

public class ScmEvaluationService {

  private ScmCriteriaService criteriaService = new ScmCriteriaService();

  public WebScmCaseStudySimulation evaluate (WebScmCaseStudySimulation  scmCss){
    // Init GitHubClient
    GitHubScmClient gitHubScmClient = GitHubScmClient.with(Level.FULL);
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.FULL);

    GitHubCommitRepository commitRepository = new GitHubCommitRepository(gitHubScmClient, gitHubApmClient, new GitHubCommitMapper(new GitHubCommitFileMapper()));

    HistoricalScmData caseStudy, simulation;

    GitHubHistoricalScmDataRepository scmHistoricalDataRepository = new GitHubHistoricalScmDataRepository(commitRepository);

    WebHistoricalScmData  caseStudyRequest = scmCss.getCaseStudy();
    caseStudy = scmHistoricalDataRepository.findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(caseStudyRequest.getRepoOwner(),
        caseStudyRequest.getRepoName(),
        caseStudyRequest.getBranch(),
        caseStudyRequest.getStartAt(),
        caseStudyRequest.getEndAt());

    //TODO same applies to scm

    return null;
  }

}
