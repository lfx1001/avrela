package es.ubu.lsi.avrela.scm.domain.model;

import lombok.Data;

@Data
public class CommitFile {

  private String name;

  private Integer additions;

  private Integer deletions;

  private String status;

}
