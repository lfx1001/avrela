package es.ubu.lsi.avrela.apm.domain.model;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Models a project issue. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Builder
public class Issue {

  /** UUID. */
  @EqualsAndHashCode.Include
  @ToString.Include
  private String id;

  /** Name. */
  @ToString.Include
  private String name;

  private String body;

  /** Sprint. */
  private Sprint sprint;

  /** State. */
  @ToString.Include
  private IssueState state;

  /** Check whether description contains task list*/
  private Boolean hasTaskList;

  /** Story points. */
  private Integer storyPoints;

  /** Creation time. */
  private ZonedDateTime createdAt;

  /** Labels. */
  private List<String> labels;

  /** Comments. */
  private List<Comment> comments;

  /** Events. */
  private List<IssueEvent> events;

  /** Assignee. */
  private String assignee;

  /** Body has links. */
  private Boolean hasLink;

  /** Images. */
  private Boolean hasImages;

  public Long countByEventType(IssueEventType eventType) {
    if (this.events == null ) {return 0L;};
    return this.events.stream()
            .filter(event -> event.getEventType() != null && eventType.equals(event.getEventType()))
            .count();
  }

  public boolean isLabeled() {
    return labels !=null && labels.size() > 0;
  }

  public boolean isLabeledWithLabel(String arg) {
    return isLabeled() && labels.contains(arg);
  }
}
