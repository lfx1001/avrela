package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.apm.adapter.github.GitHubApmClient;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubHistoricalApmDataRepository;
import es.ubu.lsi.avrela.apm.adapter.github.GitHubSprintRepository;
import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.adapter.web.mapper.WebHistoricalApmDataMapper;
import es.ubu.lsi.avrela.apm.model.HistoricalApmData;
import es.ubu.lsi.avrela.css.adapter.web.WebApmCaseStudySimulation;
import feign.Logger.Level;

public class ApmCssEvaluationService {

  public WebApmCaseStudySimulation evaluate (WebApmCaseStudySimulation apmCss){
    //Init services
    GitHubApmClient gitHubApmClient = GitHubApmClient.with(Level.BASIC);
    GitHubMilestoneMapper milestoneMapper = GitHubMilestoneMapper.build();
    GitHubSprintRepository sprintFinder = new GitHubSprintRepository(gitHubApmClient, milestoneMapper);
    GitHubHistoricalApmDataRepository apmDataRepository = new GitHubHistoricalApmDataRepository(sprintFinder);

    WebHistoricalApmData caseStudyRequest = apmCss.getCaseStudy();
    HistoricalApmData caseStudy =
        apmDataRepository.findByRepoOwnerAndRepoNameAndSprintDueBetween(
            caseStudyRequest.getRepoOwner(),
            caseStudyRequest.getRepoName(),
            caseStudyRequest.getStartAt(),
            caseStudyRequest.getEndAt());

    WebHistoricalApmDataMapper webHistoricalApmDataMapper = new WebHistoricalApmDataMapper();
    //Results
    WebApmCaseStudySimulation result = WebApmCaseStudySimulation.builder()
        .caseStudy(webHistoricalApmDataMapper.toDto(caseStudy))
        .simulation(apmCss.getSimulation()) //TODO: replace
        .rubricEvaluation(apmCss.getRubricEvaluation()) // TODO: replace
        .issueSimilarityFunctionConfig(apmCss.getIssueSimilarityFunctionConfig()) // TODO: replace
        .participants(apmCss.getParticipants())
        .build();

    return result;
  }

}
