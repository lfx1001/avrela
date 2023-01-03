package es.ubu.lsi.avrela.css.domain.model;

import es.ubu.lsi.avrela.apm.domain.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import lombok.Builder;
import lombok.Data;

/** Models a case study simulation. */
@Data
@Builder
public class ApmCaseStudySimulation {

  private HistoricalApmData caseStudy;

  private HistoricalApmData simulation;

  public List<Issue> filterIssueMatchComparisons(
      BiPredicate<Issue, Issue> comparisonMatchBiPredicate) {
    //TODO explore bipredicate usage
    List<Issue> result = new ArrayList<>();
    List<Issue> caseStudyIssues = caseStudy.filterIssues( issue -> true);
    List<Issue> simulationIssues = simulation.filterIssues( issue -> true);
    Iterator<Issue> caseStudyIssuesIterator = caseStudyIssues.iterator();
    Iterator<Issue> simulationIssuesIterator = simulationIssues.iterator();
    Issue caseStudyIssue = null;
    Issue simulationIssue = null;
    while(caseStudyIssuesIterator.hasNext() && simulationIssuesIterator.hasNext()){
      caseStudyIssue = caseStudyIssuesIterator.next();
      simulationIssue = simulationIssuesIterator.next();
      //TODO compare based on similarity:
      // - Order
      // - Title (Jaro–Winkler distance)
      // - Type
      if (caseStudyIssue.issueDescriptionMatch(simulationIssue)){
        result.add(caseStudyIssue);
      }
    }
    return result;
  }
}
