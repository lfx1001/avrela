package es.ubu.lsi.avrela.client.github;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * GitHub client integration test.
 *
 * @see <a href="https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.1/com/google/gson/TypeAdapter.html">Gson type adapter example.</a>
 */
class GitHubClientTest {

  /** Repository owner. */
  private final String owner = "davidmigloz";

  /** Repository. */
  private final String repo = "go-bees";

  /** Branch. */
  private final String branch = "master";

  /** Milestone. */
  private final Integer milestone = 1;

  private GitHubClient  gitHubClient;

  @BeforeEach
  void setUp() {
    final Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
              @Override
              public void write(JsonWriter out, ZonedDateTime value) throws IOException {}
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

  @Test
  public void commitsShouldBeRetrieved(){
    var commits = gitHubClient.findCommits(owner, repo, branch, null, LocalDateTime.now(), 1, 1);

    assertNotNull(commits, "Commit list must be none null.");
    assertTrue(commits.size()>0, "Commit list length must be greater than zero");
  }

  @Test
  public void milestonesShouldBeRetrieved(){
    var sprints = gitHubClient.findMilestones(owner, repo, 1, 1);

    assertNotNull(sprints, "Sprint list must be none null.");
    assertTrue(sprints.size()>0, "Sprint list length must be greater than zero");
  }

  @Test
  public void issuesShouldBeRetrieved(){
    var issues = gitHubClient.findIssuesByMilestone(owner, repo, milestone, 1 ,1);

    assertNotNull(issues, "Issue list must be none null.");
    assertTrue(issues.size()>0, "Issue list length must be greater than zero");
  }

  @Test
  public void sprintsInfoShouldBeComplete(){
    var sprints = gitHubClient.findMilestones(owner, repo, 1, 100);

    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getNumber() != null), "Number must be retrieved");
    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getTitle() != null), "Title must be retrieved");
    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getState() != null), "State must be retrieved");
    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getClosedAt() != null), "Closed at must be retrieved");
  }

  @Test
  public void issuesInfoShouldBeComplete(){
    var issues = gitHubClient.findIssuesByMilestone(owner, repo, milestone, 1 ,1);

    assertTrue(issues.stream().anyMatch( issue -> issue.getNumber() != null), "Number must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getMilestone() != null), "Milestone must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getTitle() != null), "Title must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getBody() != null), "Body must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getLabels() != null), "Labels must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getState() != null), "State must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getAssignee() != null), "Assignee must be retrieved");
    assertTrue(issues.stream().anyMatch( issue -> issue.getCreatedAt() != null), "Created_at must be retrieved");
  }

  @Test
  public void commitsInfoShouldBeComplete(){
    var commits = gitHubClient.findCommits(owner, repo, branch, null, LocalDateTime.now(), 1, 100);

    assertTrue(commits.stream().anyMatch( commit -> commit.getSha() != null), "SHA must be retrieved");
    assertTrue(commits.stream().anyMatch( commit -> commit.getData().getMessage() != null), "Message must be retrieved");
    assertTrue(commits.stream().anyMatch( commit -> commit.getData().getAuthor().getName() != null), "Author name must be retrieved");
    assertTrue(commits.stream().anyMatch( commit -> commit.getData().getAuthor().getDate() != null), "Date must be retrieved");
  }

}