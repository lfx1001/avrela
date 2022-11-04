package es.ubu.lsi.avrela.domain;

import es.ubu.lsi.avrela.apm.domain.model.Issue;
import es.ubu.lsi.avrela.apm.domain.model.Sprint;
import es.ubu.lsi.avrela.scm.domain.model.Commit;
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
