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
   * @return participants in time box.
   */
  public Set<String> getParticipants(){
    Set<String> result = new HashSet<>();
    sprints.forEach(
        sprint -> {
          result.addAll(sprint.getParticipants());
        }
    );
    return result;
  }

  /**
   * Count participants in time box.
   * @return number of participants.
   */
  public Integer countUsers(){
    return getParticipants().size();
  }

  public Long countIssues(){
    Long result = 0L;
    for(Sprint sprint: sprints){
      result += sprint.countIssues();
    }
    return result;
  }

}
