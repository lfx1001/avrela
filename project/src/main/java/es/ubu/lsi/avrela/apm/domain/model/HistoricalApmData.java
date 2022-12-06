package es.ubu.lsi.avrela.apm.domain.model;


import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Historical Agile Project Management data.
 */
@Data
@Builder
public class HistoricalApmData {

  private String repoOwner;

  private String repoName;

  private ZonedDateTime startAt;

  private ZonedDateTime endAt;

  /*
   *  Sprints ordered by date desc.
   */
  private List<Sprint> sprints;

}
