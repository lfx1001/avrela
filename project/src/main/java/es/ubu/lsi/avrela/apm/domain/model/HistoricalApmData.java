package es.ubu.lsi.avrela.apm.domain.model;


import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  /**
   *
   * @return users who has performed actions in the simulation
   */
  public Set<String> getUsers(){
    Set<String> result = new HashSet<>();
    sprints.forEach(
        sprint -> {
          result.addAll(sprint.getUsers());
        }
    );
    return result;
  }

  /**
   *
   * @return total number of unique users
   */
  public Integer countUsers(){
    return getUsers().size();
  }

}
