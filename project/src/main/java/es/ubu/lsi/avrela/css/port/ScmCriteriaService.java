package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import java.util.Set;

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
}
