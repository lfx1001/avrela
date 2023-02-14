package es.ubu.lsi.avrela.css.model;

import es.ubu.lsi.avrela.scm.model.Commit;
import es.ubu.lsi.avrela.scm.model.CommitSimilarity.Feature;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScmCaseStudySimulation {

  private HistoricalScmData caseStudy;

  private HistoricalScmData simulation;
  public List<Commit> filterCommitMatchComparison(EnumMap<Feature, Double> featureWeights, int commitSimilarityThreshold) {
    return Collections.emptyList();
  }
}
