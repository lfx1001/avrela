package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebScmCaseStudySimulation {

  private WebHistoricalApmData caseStudy;

  private WebHistoricalApmData simulation;

  private Integer participants;

  private WebCommitSimilarityFunctionConfig commitSimilarityFunctionConfig;

  private Integer similarityThreshold;

  private ScmWebRubricEvaluation  rubricEvaluation;

}
