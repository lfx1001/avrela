package es.ubu.lsi.avrela.apm.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;



/** Models a Sprint. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
public class Sprint {

  /** UID */
  @EqualsAndHashCode.Include
  private String id;

  /** State. */
  private SprintState state;

  /** Issues */
  private List<Issue> issues;

}
