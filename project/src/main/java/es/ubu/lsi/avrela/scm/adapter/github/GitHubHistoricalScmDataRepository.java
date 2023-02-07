package es.ubu.lsi.avrela.scm.adapter.github;

import es.ubu.lsi.avrela.scm.domain.model.HistoricalScmData;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubHistoricalScmDataRepository implements
    es.ubu.lsi.avrela.scm.port.HistoricalScmDataRepository {

  private final GitHubCommitRepository gitHubCommitRepository;

  @Override
  public HistoricalScmData findByRepoOwnerAndRepoNameAndBranchAndDatesBetween(
      String repoOwner, String repoName, String branch, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    return null;
  }
}
