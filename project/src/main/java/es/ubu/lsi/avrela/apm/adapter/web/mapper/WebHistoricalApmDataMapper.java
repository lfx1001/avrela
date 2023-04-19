package es.ubu.lsi.avrela.apm.adapter.web.mapper;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.model.HistoricalApmData;

public class WebHistoricalApmDataMapper {

  public WebHistoricalApmData toDto(HistoricalApmData domain){
    return WebHistoricalApmData.builder()
        .repoOwner(domain.getRepoOwner())
        .repoName(domain.getRepoName())
        .startAt(domain.getStartAt())
        .endAt(domain.getEndAt())
        .sprints(domain.getSprints())
        .build();
  }

}
