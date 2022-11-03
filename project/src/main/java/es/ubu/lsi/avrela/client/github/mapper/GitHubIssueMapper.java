package es.ubu.lsi.avrela.client.github.mapper;

import es.ubu.lsi.avrela.client.github.GitHubComment;
import es.ubu.lsi.avrela.client.github.GitHubIssue;
import es.ubu.lsi.avrela.domain.Issue;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubIssueMapper {

  private final GitHubCommentMapper commentMapper;

  public Issue toDomain(GitHubIssue issue, List<GitHubComment> comments) {
    if (issue == null) {return null;}
    Issue result = Issue.builder()
        .id(issue.getNumber().toString())
        .name(issue.getTitle())
        .hasTaskList(issue.hasTaskList())
        .body(issue.getBody())
        .createdAt(issue.getCreatedAt())
        .assignee(issue.getAssignee().getLogin())
        .comments(commentMapper.toDomain(comments))
        .build();
    return result;
  }
}
