package es.ubu.lsi.avrela.apm.adapter.github;

import es.ubu.lsi.avrela.apm.adapter.github.mapper.GitHubIssueMapper;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubComment;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssueEvent;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import es.ubu.lsi.avrela.apm.port.IssueFinder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GitHubIssueFinder implements IssueFinder {

  private final GitHubClient gitHubClient;

  private final GitHubIssueMapper gitHubIssueMapper;


  @Override
  public Issue findById(String repoOwner, String repoName, String issueId) {
    log.debug("Using repository identified by owner [{}] and name [{}]", repoOwner, repoName);
    log.debug("Fetching issue with id [{}]", issueId);
    GitHubIssue issue = gitHubClient.findIssueById(repoOwner, repoName, issueId);
    log.debug("Fetching issue [{}] events", issue.getTitle());
    List<GitHubIssueEvent> events = gitHubClient.findEventsByIssue(repoOwner, repoName, issue.getNumber().toString());
    log.debug("Fetched [{}] events", events.size());
    List<GitHubIssueEvent> filteredEvents = new ArrayList<>();
    events.stream().forEach( event -> {
          log.debug("Processing event [{}] of issue [{}]", event, issue.getTitle());
          if (event.getType() != null && GitHubIssueEvent.TYPES_SUPPORTED.contains(event.getType())){
            filteredEvents.add(event);
          }
        }
    );
    issue.setEvents(filteredEvents);

    if(issue.getTotalComments() > 0){
      //TODO: Paginate comments. Currently, only 100 comments per Issue are fetched.
      log.debug("Fetching issue [{}] comments", issue.getTitle());
      List<GitHubComment> comments = gitHubClient.findCommentsByIssue(repoOwner, repoName,
          issue.getNumber().toString());
      log.debug("[{}] comments fetched", comments.size());
      issue.setComments(comments);
    }

    return gitHubIssueMapper.toDomain(issue);
  }

}
