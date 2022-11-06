package es.ubu.lsi.avrela.apm.adapter.github;

import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubMilestoneMapper;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubComment;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubMilestone;
import es.ubu.lsi.avrela.apm.domain.model.Sprint;
import es.ubu.lsi.avrela.apm.port.SprintFinder;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GitHubSprintFinder implements SprintFinder {

  private GitHubClient gitHubClient;

  private GitHubMilestoneMapper gitHubMilestoneMapper;

  @Override
  public List<Sprint> findByDueOnBetween(String repoOwner, String repoName, ZonedDateTime startAt,
      ZonedDateTime endAt) {
    log.debug("Using repository identified by owner [{}] and name [{}]", repoOwner, repoName);
    //TODO: review MAX page size. GitHub Milestones REST API cannot filter by dates.
    log.debug("Fetching milestones between [{}] and [{}]", startAt, endAt);
    List<GitHubMilestone> milestones = gitHubClient.findMilestones(repoOwner, repoName, 1, 100);
    if (milestones == null && milestones.size() == 0) {
      return new ArrayList<>();
    }

    List<GitHubMilestone> targetMilestones = milestones
        .stream()
        .filter(milestone -> milestone.getDueOn().isAfter(startAt) && milestone.getDueOn()
            .isBefore(endAt))
        .collect(Collectors.toList());
    log.debug("[{}] sprints fetched", targetMilestones.size());

    for (GitHubMilestone milestone : targetMilestones) {
      //TODO: Paginate issues. Currently, only 100 issues per Sprint are fetched.
      List<GitHubIssue> issues = gitHubClient.findIssuesByMilestone(repoOwner, repoName,
          milestone.getNumber(), 1, 100);
      for (GitHubIssue issue : issues) {
        //TODO: Paginate comments. Currently, only 100 comments per Issue are fetched.
        List<GitHubComment> comments = gitHubClient.findCommentsByIssue(repoOwner, repoName,
            issue.getNumber().toString());
        issue.setComments(comments);
      }
      milestone.setIssues(issues);
    }

    return gitHubMilestoneMapper.toDomain(targetMilestones);
  }

}
