package es.ubu.lsi.avrela.client.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubComment;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import es.ubu.lsi.avrela.apm.domain.model.IssueState;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubIssueMapper {

  private final GitHubCommentMapper commentMapper;
  private final GitHubLabelMapper labelMapper;

  public Issue toDomain(GitHubIssue issue, List<GitHubComment> comments) {
    if (issue == null) {return null;}
    Issue result = Issue.builder()
        .id(issue.getNumber().toString())
        .name(issue.getTitle())
        .hasTaskList(issue.hasTaskList())
        .state(IssueState.valueOf(issue.getState().name()))
        .body(issue.getBody())
        .createdAt(issue.getCreatedAt())
        .assignee(issue.getAssignee().getLogin())
        .comments(commentMapper.toDomain(comments))
        .labels(labelMapper.toDomain(issue.getLabels()))
        .build();
    return result;
  }
}
