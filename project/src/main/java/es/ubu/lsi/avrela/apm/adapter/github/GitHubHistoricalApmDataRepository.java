package es.ubu.lsi.avrela.apm.adapter.github;

import es.ubu.lsi.avrela.apm.domain.model.HistoricalApmData;
import es.ubu.lsi.avrela.apm.port.HistoricalApmDataRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubHistoricalApmDataRepository implements HistoricalApmDataRepository {

  private final GitHubSprintFinder sprintFinder;

  @Override
  public HistoricalApmData findByRepoOwnerAndRepoNameAndSprintDueBetween(String repoOwner,
      String repoName, ZonedDateTime startAt, ZonedDateTime endAt) {
    return HistoricalApmData.builder()
        .repoOwner(repoOwner)
        .repoName(repoName)
        .sprints(sprintFinder.findByDueOnBetween(repoOwner, repoName,startAt, endAt))
        .build();
  }
}
