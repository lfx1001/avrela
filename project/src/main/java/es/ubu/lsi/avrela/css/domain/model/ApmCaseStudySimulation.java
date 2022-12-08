package es.ubu.lsi.avrela.css.domain.model;

import es.ubu.lsi.avrela.apm.domain.model.HistoricalApmData;
import lombok.Data;

/** Models a case study simulation. */
@Data
public class ApmCaseStudySimulation {

  private HistoricalApmData caseStudy;

  private HistoricalApmData simulation;

}
