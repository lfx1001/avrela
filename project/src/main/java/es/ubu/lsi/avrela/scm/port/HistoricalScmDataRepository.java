package es.ubu.lsi.avrela.scm.port;

import es.ubu.lsi.avrela.scm.domain.model.HistoricalScmData;
import java.time.ZonedDateTime;

public interface HistoricalScmDataRepository {

  HistoricalScmData findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
      String repoOwner, String repoName, String branch, ZonedDateTime startAt,
      ZonedDateTime endAt);
}
