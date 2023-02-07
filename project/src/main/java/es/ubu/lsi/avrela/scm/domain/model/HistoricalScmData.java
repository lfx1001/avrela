package es.ubu.lsi.avrela.scm.domain.model;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoricalScmData {

  private String repoOwner;

  private String repoName;

  private String branch;

  private ZonedDateTime startAt;

  private ZonedDateTime endAt;

  /*
   *  Commits ordered by date desc.
   */
  private List<Commit> commits;

}
