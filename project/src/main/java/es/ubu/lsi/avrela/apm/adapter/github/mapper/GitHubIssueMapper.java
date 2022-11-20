package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import es.ubu.lsi.avrela.apm.domain.model.IssueState;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubIssueMapper {

  private final GitHubCommentMapper commentMapper;
  private final GitHubLabelMapper labelMapper;

  private final GitHubIssueEventMapper eventMapper;

  public Issue toDomain(GitHubIssue issue) {
    if (issue == null) {return null;}
    return Issue.builder()
        .id(issue.getNumber().toString())
        .name(issue.getTitle())
        .hasTaskList(issue.hasTaskList())
        .state(IssueState.valueOf(issue.getState().name()))
        .body(issue.getBody())
        .createdAt(issue.getCreatedAt())
        .assignee(issue.getAssignee().getLogin())
        .comments(commentMapper.toDomain(issue.getComments()))
        .labels(labelMapper.toDomain(issue.getLabels()))
        .events(eventMapper.toDomain(issue.getEvents()))
        .build();
  }

  public List<Issue> toDomain(List<GitHubIssue> issues){
    if (issues == null){
      return new ArrayList<>();
    }
    List<Issue> result = new ArrayList<>(issues.size());
    for(GitHubIssue issue : issues){
      result.add(toDomain(issue));
    }
    return result;
  }

}
