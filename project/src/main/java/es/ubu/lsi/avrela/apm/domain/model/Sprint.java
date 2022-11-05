package es.ubu.lsi.avrela.apm.domain.model;

import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;



/** Models a Sprint. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@Builder
public class Sprint {

  /** UID */
  @EqualsAndHashCode.Include
  private String id;

  private String title;

  private String description;

  /** State. */
  private SprintState state;

  /** Issues */
  private List<Issue> issues;

  private ZonedDateTime dueOn;

}
