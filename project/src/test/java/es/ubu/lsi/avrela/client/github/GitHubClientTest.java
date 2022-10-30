package es.ubu.lsi.avrela.client.github;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import feign.Feign;
import feign.Logger.Level;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * GitHub client integration test.
 *
 * @see <a
 * href="https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/com/google/gson/TypeAdapter.html">Gson
 * type adapter example.</a>
 */
class GitHubClientTest {

  /**
   * GitHub Client.
   */
  private static GitHubClient gitHubClient;
  /**
   * Repository owner.
   */
  private final String owner = "davidmigloz";
  /**
   * Repository.
   */
  private final String repo = "go-bees";
  /**
   * Branch.
   */
  private final String branch = "master";
  /**
   * Milestone.
   */
  private final Integer milestone = 1;
  /**
   * Issue.
   */
  private final String issueWithComments = "202";

  @BeforeAll
  public static void setUp() {
    final Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
              @Override
              public void write(JsonWriter out, ZonedDateTime value) throws IOException {
              }

              @Override
              public ZonedDateTime read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                  in.nextNull();
                  return null;
                }
                return ZonedDateTime.parse(in.nextString());
              }
            })
            .serializeNulls()
            .create();
    final Decoder decoder = new GsonDecoder(gson);

    gitHubClient = Feign.builder()
        .logger(new Slf4jLogger(GitHubClient.class))
        .encoder(new GsonEncoder())
        .decoder(decoder)
        .logLevel(Level.FULL)
        .target(GitHubClient.class, "https://api.github.com");
  }

  @Nested
  @DisplayName("Given a GitHub repository")
  public class GitHubCommitsTest {

    @Nested
    @DisplayName("When repository has commits")
    public class GitHubRepositoryWithCommits {

      @Test
      @DisplayName("Then commits should be fetched")
      public void commitsShouldBeRetrieved() {
        var commits = gitHubClient.findCommits(owner, repo, branch, null, LocalDateTime.now(), 1,
            1);

        assertNotNull(commits, "Commit list must be none null.");
        assertTrue(commits.size() > 0, "Commit list length must be greater than zero");
      }

      @Test
      @DisplayName("Then commit relevant info should be fetched")
      public void commitsInfoShouldBeComplete() {
        var commits = gitHubClient.findCommits(owner, repo, branch, null, LocalDateTime.now(), 1,
            100);

        assertAll("Verify relevant info is present",
            () -> assertTrue(commits.stream().anyMatch(commit -> commit.getSha() != null),
                "SHA must be retrieved"),
            () -> assertTrue(
                commits.stream().anyMatch(commit -> commit.getData().getMessage() != null),
                "Message must be retrieved"),
            () -> assertTrue(
                commits.stream().anyMatch(commit -> commit.getData().getAuthor().getName() != null),
                "Author name must be retrieved"),
            () -> assertTrue(
                commits.stream().anyMatch(commit -> commit.getData().getAuthor().getDate() != null),
                "Date must be retrieved"));
      }
    }
  }

  @Nested
  @DisplayName("Given a GitHub repository")
  class GitHubSprintsTest {

    @Nested
    @DisplayName("When repository has milestones")
    class RepositoryWithMilestones {

      @Test
      @DisplayName("Then milestones should be fetched")
      public void milestonesShouldBeRetrieved() {
        var milestones = gitHubClient.findMilestones(owner, repo, 1, 1);

        assertNotNull(milestones, "Milestones list must be none null.");
        assertTrue(milestones.size() > 0, "Milestones list is not empty");
      }

      @Test
      @DisplayName("Then milestone relevant info should be fetched")
      public void milestonesInfoShouldBeComplete() {
        var milestones = gitHubClient.findMilestones(owner, repo, 1, 100);

        assertAll("Verify milestone  relevant info is present",
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getNumber() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getNumber() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getNumber() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getTitle() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getState() != null)),
            () -> assertTrue(
                milestones.stream().anyMatch(milestone -> milestone.getClosedAt() != null))
        );
      }

    }

  }

  @Nested
  @DisplayName("Given a GitHub repository")
  class GitHubIssuesTest {

    @Nested
    @DisplayName("When repository has issues")
    class RepositoryWithIssues {

      @Test
      @DisplayName("Then issues should be fetched")
      public void issuesShouldBeRetrieved() {
        var issues = gitHubClient.findIssuesByMilestone(owner, repo, milestone, 1, 1);

        assertNotNull(issues, "Issue list must be none null.");
        assertTrue(issues.size() > 0, "Issue list length must be greater than zero");
      }

      @Test
      @DisplayName("Then issue relevant info should be fetched")
      public void issuesInfoShouldBeComplete() {
        var issues = gitHubClient.findIssuesByMilestone(owner, repo, milestone, 1, 1);

        assertAll("Verify all relevant issue info is present",
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getNumber() != null),
                "Number must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getMilestone() != null),
                "Milestone must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getTitle() != null),
                "Title must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getBody() != null),
                "Body must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getLabels() != null),
                "Labels must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getState() != null),
                "State must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getAssignee() != null),
                "Assignee must be retrieved"),
            () -> assertTrue(issues.stream().anyMatch(issue -> issue.getCreatedAt() != null),
                "Created_at must be retrieved")
        );
      }
    }
  }

  @Nested
  @DisplayName("Given an issue")
  public class GitHubCommentsTest {

    @Nested
    @DisplayName("When issue has comments")
    public class GitHubIssueWithComments {

      @Test
      @DisplayName("Then issues should be fetched")
      public void commentsShouldBeRetrieved() {
        var comments = gitHubClient.findCommentsByIssue(owner, repo, issueWithComments);

        assertNotNull(comments, "Comment list must be none null.");
        assertTrue(comments.size() > 0, "Comment list length must be greater than zero");
      }

      @Test
      @DisplayName("Then issue relevant info should be fetched")
      public void commentsInfoShouldBeComplete() {
        var comments = gitHubClient.findCommentsByIssue(owner, repo, issueWithComments);

        assertAll("Verify comment data",
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getId() != null),
                "Id must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getBody() != null),
                "Body must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getUser() != null),
                "User must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getCreatedAt() != null),
                "Created at must be retrieved"),
            () -> assertTrue(comments.stream().anyMatch(comment -> comment.getUpdatedAt() != null),
                "Updated at must be retrieved")
        );
      }

    }

  }

}