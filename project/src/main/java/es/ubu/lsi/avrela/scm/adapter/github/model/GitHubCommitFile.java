package es.ubu.lsi.avrela.scm.adapter.github.model;

import lombok.Data;

/**
 * Models a GitHub commit.
 */
@Data
public class GitHubCommitFile {

  String filename;

  Integer additions;

  Integer deletions;

  String status;

}
