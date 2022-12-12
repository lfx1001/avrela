package es.ubu.lsi.avrela.css.domain.model;

import es.ubu.lsi.avrela.apm.domain.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import java.util.List;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Data;

/** Models a case study simulation. */
@Data
@Builder
public class ApmCaseStudySimulation {

  private HistoricalApmData caseStudy;

  private HistoricalApmData simulation;

  public List<Issue> filterIssueMatchComparisons(Predicate<Issue> comparisonMatchPredicate) {
    return List.of(Issue.builder().build());
  }
}
