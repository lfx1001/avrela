package es.ubu.lsi.avrela.apm.adapter.web;


import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.apm.model.Sprint;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Historical Agile Project Management data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebHistoricalApmData {

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
  public Integer countParticipants(){
    return getParticipants().size();
  }

  public Long countIssues(){
    Long result = 0L;
    for(Sprint sprint: sprints){
      result += sprint.countIssues();
    }
    return result;
  }

  /**
   * Filter issues
   * @param filter
   * @return filtered issues
   */
  public List<Issue> filterIssues(Predicate<Issue> filter){
    return sprints.stream()
        .flatMap( sprint -> sprint.getIssues().stream()
            .filter(filter)
        )
        .collect(Collectors.toList());
  }

}
