package es.ubu.lsi.avrela.css.util;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.apm.model.IssueState;
import es.ubu.lsi.avrela.apm.model.Sprint;
import es.ubu.lsi.avrela.css.adapter.web.ScmWebRubricEvaluation;
import es.ubu.lsi.avrela.css.adapter.web.WebCommitSimilarityFunctionConfig;
import es.ubu.lsi.avrela.css.adapter.web.WebRubricCriteriaEvaluation;
import es.ubu.lsi.avrela.css.adapter.web.WebScmCaseStudySimulation;
import java.util.List;

public class ScmCssDataGenerator {

  public static WebScmCaseStudySimulation getWebScmCaseStudySimulation() {
    return WebScmCaseStudySimulation.builder()
        .caseStudy(getWebHistoricalApmData())
        .simulation(getWebHistoricalApmData())
        .participants(1)
        .rubricEvaluation(getWebRubricEvaluation())
        .similarityThreshold(75)
        .commitSimilarityFunctionConfig(
            getSimilarityFunctionConfig()
        )
        .build();
  }

  public static ScmWebRubricEvaluation getWebRubricEvaluation() {
    return ScmWebRubricEvaluation.builder()
        .teamWork(new WebRubricCriteriaEvaluation(100d, 2))
        .similarity(new WebRubricCriteriaEvaluation(78d, 1))
        .issueTraceability(new WebRubricCriteriaEvaluation(90d, 2))
        .build();
  }

  public static WebCommitSimilarityFunctionConfig getSimilarityFunctionConfig() {
    return WebCommitSimilarityFunctionConfig.builder()
        .messageWeight(1.0)
        .filesWeight(1.0)
        .build();
  }

  private static WebHistoricalApmData getWebHistoricalApmData() {
    return WebHistoricalApmData.builder()
        .repoOwner("davidmigloz")
        .repoName("go-bees")
        .stringifyStartAt("2017-01-25")
        .stringifyEndAt("2017-01-26")
        .sprints(
            List.of(
                Sprint.builder()
                    .issues(
                        List.of(
                            Issue.builder()
                                .id("Id")
                                .name("Issue Name")
                                .state(IssueState.OPEN)
                                .body("Loren ipsum, loren ipsum, loren ipsum, loren ipsum ...")
                                .labels(
                                    List.of(
                                        "label1",
                                        "label2",
                                        "label3"
                                    )
                                )
                                .hasImages(Boolean.FALSE)
                                .hasLink(Boolean.TRUE)
                                .hasTaskList(Boolean.FALSE)
                                .build()
                        )
                    )
                    .build()
            )
        )
        .build();
  }

  public static WebScmCaseStudySimulation webScmCaseStudySimulationBeforeEvaluation(){
    WebScmCaseStudySimulation result = getWebScmCaseStudySimulation();
    result.setRubricEvaluation(null);
    return result;
  }
}
