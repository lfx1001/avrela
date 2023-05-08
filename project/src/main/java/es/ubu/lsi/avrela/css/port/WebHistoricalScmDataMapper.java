package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.css.adapter.web.WebHistoricalScmData;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;

public class WebHistoricalScmDataMapper {

  public WebHistoricalScmData toDto(HistoricalScmData domain) {
    return WebHistoricalScmData.builder()
        .repoOwner(domain.getRepoOwner())
        .repoName(domain.getRepoName())
        .branch(domain.getBranch())
        .startAt(domain.getStartAt())
        .endAt(domain.getEndAt())
        .commits(domain.getCommits())
        .build();
  }
}
