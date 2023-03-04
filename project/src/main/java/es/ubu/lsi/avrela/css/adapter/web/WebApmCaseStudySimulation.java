package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebApmCaseStudySimulation {

  private WebHistoricalApmData caseStudy;

  private WebHistoricalApmData simulation;

}
