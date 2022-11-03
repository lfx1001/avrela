package es.ubu.lsi.avrela.domain;

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

  /** Assignee. */
  private String assignee;

}
