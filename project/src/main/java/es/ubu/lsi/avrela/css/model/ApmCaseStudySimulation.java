package es.ubu.lsi.avrela.css.model;

import es.ubu.lsi.avrela.apm.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.model.Issue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/** Models a case study simulation. */
@Data
@Builder
public class ApmCaseStudySimulation {

  private HistoricalApmData caseStudy;

  private HistoricalApmData simulation;

  public List<Issue> filterIssueMatchComparisons() {
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
