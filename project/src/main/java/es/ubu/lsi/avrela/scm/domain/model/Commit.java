package es.ubu.lsi.avrela.scm.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Models a commit. */
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Builder
public class Commit {

  /** Unique identifier generated by Git. */
  private String sha;

  /** Date. */
  private LocalDateTime date;

  /** Author. */
  private String author;

  /** Message. */
  private String message;

}
