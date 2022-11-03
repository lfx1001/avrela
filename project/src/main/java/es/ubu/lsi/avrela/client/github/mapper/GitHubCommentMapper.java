package es.ubu.lsi.avrela.client.github.mapper;

import es.ubu.lsi.avrela.client.github.GitHubComment;
import es.ubu.lsi.avrela.domain.Comment;
import java.util.List;
import java.util.stream.Collectors;

public class GitHubCommentMapper {

  public static Comment toDomain(GitHubComment comment){
    Comment result = new Comment();
    result.setBody(comment.getBody());
    result.setAuthor(comment.getUser().getLogin());
    return result;
  }

  public List<Comment> toDomain(List<GitHubComment> comments){
    if (comments == null){
      return null;
    }
    return comments.stream()
        .map(GitHubCommentMapper::toDomain)
        .collect(Collectors.toList());
  }

}
