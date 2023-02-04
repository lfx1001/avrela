package es.ubu.lsi.avrela.scm.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommitFile {

  private String name;

  private Integer additions;

  private Integer deletions;

  private String status;

}
