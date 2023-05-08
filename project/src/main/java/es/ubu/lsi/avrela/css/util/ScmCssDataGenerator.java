package es.ubu.lsi.avrela.css.util;

import es.ubu.lsi.avrela.css.adapter.web.ScmWebRubricEvaluation;
import es.ubu.lsi.avrela.css.adapter.web.WebCommitSimilarityFunctionConfig;
import es.ubu.lsi.avrela.css.adapter.web.WebHistoricalScmData;
import es.ubu.lsi.avrela.css.adapter.web.WebRubricCriteriaEvaluation;
import es.ubu.lsi.avrela.css.adapter.web.WebScmCaseStudySimulation;
import java.util.Collections;

public class ScmCssDataGenerator {

  public static WebScmCaseStudySimulation getWebScmCaseStudySimulation() {
    return WebScmCaseStudySimulation.builder()
        .caseStudy(getWebHistoricalScmData())
        .simulation(getWebHistoricalScmData())
        .participants(1)
        .similarityThreshold(75)
        .commitSimilarityFunctionConfig(
            getSimilarityFunctionConfig()
        )
        .rubricEvaluation(getScmWebRubricEvaluation())
        .build();
  }

  public static ScmWebRubricEvaluation getScmWebRubricEvaluation() {
    return ScmWebRubricEvaluation.builder()
        .teamWork(new WebRubricCriteriaEvaluation(100d, 2))
        .similarity(new WebRubricCriteriaEvaluation(100d, 2))
        .issueTraceability(new WebRubricCriteriaEvaluation(50d, 2))
        .build();
  }

  public static WebCommitSimilarityFunctionConfig getSimilarityFunctionConfig() {
    return WebCommitSimilarityFunctionConfig.builder()
        .messageWeight(1.0)
        .filesWeight(1.0)
        .build();
  }

  private static WebHistoricalScmData getWebHistoricalScmData() {
    return WebHistoricalScmData.builder()
        .repoOwner("davidmigloz")
        .repoName("go-bees")
        .branch("master")
        .stringifyStartAt("2017-01-25")
        .stringifyEndAt("2017-01-25")
        .commits(Collections.emptyList())
        .build();
  }

  public static WebScmCaseStudySimulation webScmCaseStudySimulationBeforeEvaluation(){
    WebScmCaseStudySimulation result = getWebScmCaseStudySimulation();
    result.setRubricEvaluation(null);
    return result;
  }
}
