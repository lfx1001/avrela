package es.ubu.lsi.avrela.client.github.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import es.ubu.lsi.avrela.client.github.GitHubComment;
import es.ubu.lsi.avrela.client.github.GitHubIssue;
import es.ubu.lsi.avrela.client.github.GitHubUser;
import es.ubu.lsi.avrela.domain.Issue;
import java.time.ZonedDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GitHubIssueMapperTest {

  public GitHubIssueMapper commentMapper = new GitHubIssueMapper(new GitHubCommentMapper());

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
        GitHubIssue issue = GitHubIssue.builder()
            .body("The issue body")
            .title("Issue title")
            .number(1)
            .createdAt(ZonedDateTime.now())
            .assignee( new GitHubUser("Assignee"))
            .build();

        List<GitHubComment> comments = null;

        Issue result = commentMapper.toDomain(issue, comments);

        assertNotNull(result);
        assertAll("Verify mapping result",
            ()-> assertEquals(issue.getTitle(), result.getName()),
            ()-> assertEquals(issue.getBody(), result.getBody()),
            ()-> assertEquals(issue.getNumber().toString(), result.getId()),
            ()-> assertEquals(issue.getCreatedAt(), result.getCreatedAt()),
            ()-> assertEquals(issue.getAssignee().getLogin(), result.getAssignee())
        );
      }
    }
  }


}
