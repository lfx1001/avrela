package es.ubu.lsi.avrela.scm.port;

import es.ubu.lsi.avrela.scm.domain.model.Commit;

public interface CommitRepository {

  /**
   *
   * @param owner
   * @param repo
   * @param sha
   * @return
   */
  Commit findCommit(String owner,String repo,String sha);

}
