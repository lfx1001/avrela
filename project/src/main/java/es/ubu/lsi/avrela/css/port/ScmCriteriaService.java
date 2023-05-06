package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.css.model.ScmCaseStudySimulation;
import es.ubu.lsi.avrela.scm.model.CommitSimilarity.Feature;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import java.util.EnumMap;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ScmCriteriaService {

  public Integer teamWorkEvaluation(HistoricalScmData simulation, Integer simulationParticipants) {
    Set<String> participants = simulation.getParticipants();
    //Evaluate agile fidelity (authors alternate commits)
    if (simulationParticipants.equals(simulation.getParticipants().size())){
      if (simulation.alternativeCommits(simulationParticipants)){
        return 2;
      } else {
        return 1;
      }
    }else {
      return 0;
    }
  }

  public Double getCommitSimilarity(ScmCaseStudySimulation scmCaseStudySimulation, EnumMap<Feature, Double> featureWeights, int similarityThreshold){
    Double result;
    Integer commitSimilarityDividend = scmCaseStudySimulation.filterCommitMatchComparison(featureWeights, similarityThreshold).size();
    log.debug( "Found [{}] similar commits", commitSimilarityDividend);
    Integer commitSimilarityDivisor = scmCaseStudySimulation.getCaseStudy().getCommits().size();

    result = 100*Double.valueOf(commitSimilarityDividend / commitSimilarityDivisor);
    log.debug( "Commit similarity value is [{}]", result);
    return result;
  }

  public Double getCommitsWithIssueTraceability(ScmCaseStudySimulation scmCaseStudySimulation){
    Integer commitsWithIssueTraceabilityDividend = scmCaseStudySimulation
        .getSimulation()
        .getCommitsWithIssueTraceability()
        .size();

    Integer commitsWithIssueTraceabilityDivisor = scmCaseStudySimulation.getCaseStudy().getCommits().size();

    Double result = 100*Double.valueOf((double)commitsWithIssueTraceabilityDividend / commitsWithIssueTraceabilityDivisor);
    log.debug( "Issue traceability [{}]/[{}] adjusted is [{}]", commitsWithIssueTraceabilityDividend, commitsWithIssueTraceabilityDivisor, result);
    return result;
  }
}
