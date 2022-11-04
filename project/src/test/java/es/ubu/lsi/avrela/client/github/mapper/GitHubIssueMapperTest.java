package es.ubu.lsi.avrela.client.github.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubComment;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubItemState;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubUser;
import es.ubu.lsi.avrela.apm.domain.model.Issue;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GitHubIssueMapperTest {

  public GitHubIssueMapper commentMapper = new GitHubIssueMapper(new GitHubCommentMapper(), new GitHubLabelMapper());

  @Nested
  @DisplayName("Given a null GitHubIssue")
  public class  NullGitHubIssue {

    @Nested
    @DisplayName("When I map the GitHub issue to domain entity")
    public class MapNullGitHubIssue{

      @Test
      @DisplayName("Then result should be null")
      public void shouldReturnNull(){
        GitHubIssue issue = null;
        List<GitHubComment> comments = null;

        Issue result = commentMapper.toDomain(issue, comments);
        assertNull(result);
      }
    }
  }

  @Nested
  @DisplayName("Given a none null GitHubIssue")
  public class  NoneNullGitHubIssue {

    @Nested
    @DisplayName("When I map the GitHub issue to domain entity")
    public class MapNoneNullGitHubIssue{

      @Test
      @DisplayName("Then domain entity should contain the information needed")
      public void shouldReturnDomainEntity(){
        GitHubIssue issue = getGitHubIssue();
        List<GitHubComment> comments = getGitHubComments();

        Issue result = commentMapper.toDomain(issue, comments);

        assertNotNull(result);
        assertAll("Verify mapping result",
            ()-> assertEquals(issue.getTitle(), result.getName()),
            ()-> assertEquals(issue.getBody(), result.getBody()),
            ()-> assertEquals(issue.getNumber().toString(), result.getId()),
            ()-> assertEquals(issue.getCreatedAt(), result.getCreatedAt()),
            ()-> assertEquals(issue.getAssignee().getLogin(), result.getAssignee()),
            ()-> assertEquals(issue.getState().name(), result.getState().name()),
            ()-> assertNotNull(result.getComments())
        );
      }
    }

    private GitHubIssue getGitHubIssue() {
      GitHubIssue issue = GitHubIssue.builder()
          .body("The issue body")
          .title("Issue title")
          .number(1)
          .createdAt(ZonedDateTime.now())
          .state(GitHubItemState.OPEN)
          .assignee( new GitHubUser("Assignee"))
          .build();
      return issue;
    }
  }

  private static List<GitHubComment> getGitHubComments() {
    List<GitHubComment> comments = new ArrayList<>();
    GitHubComment comment = GitHubComment.builder()
        .id("id")
        .body("comment body")
        .user(new GitHubUser("author"))
        .createdAt(ZonedDateTime.now())
        .build();
    return comments;
  }


}
