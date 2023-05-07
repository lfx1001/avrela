package es.ubu.lsi.avrela.css.adapter.web;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebScmCaseStudySimulation {

  private WebHistoricalScmData caseStudy;

  private WebHistoricalScmData simulation;

  private Integer participants;

  private WebCommitSimilarityFunctionConfig commitSimilarityFunctionConfig;

  private Integer similarityThreshold;

  private ScmWebRubricEvaluation  rubricEvaluation;

}
