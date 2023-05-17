package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.css.model.CommitComparison;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebApmCaseStudySimulation {

  private WebHistoricalApmData caseStudy;

  private WebHistoricalApmData simulation;

  private Integer participants;

  private List<CommitComparison> commitComparisons;

  private WebIssueSimilarityFunctionConfig issueSimilarityFunctionConfig;

  private WebRubricEvaluation  rubricEvaluation;

}
