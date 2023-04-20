package es.ubu.lsi.avrela.apm.adapter.web.mapper;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.model.HistoricalApmData;

public class WebHistoricalApmDataMapper {

  public WebHistoricalApmData toDto(HistoricalApmData domain){
    WebHistoricalApmData result = WebHistoricalApmData.builder()
        .repoOwner(domain.getRepoOwner())
        .repoName(domain.getRepoName())
        .sprints(domain.getSprints())
        .build();
    result.setStartAt(domain.getStartAt());
    result.setEndAt(domain.getEndAt());
    return result;
  }

}
