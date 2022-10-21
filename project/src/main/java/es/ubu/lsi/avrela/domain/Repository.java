package es.ubu.lsi.avrela.domain;

import java.util.List;

/** Models a repository. */
public class Repository {

  /** Sprints. */
  private List<Sprint> sprints;

  /** Issues. */
  private List<Issue> issues;

  /** Commit list ordered by date asc. */
  private List<Commit> commits;

}
