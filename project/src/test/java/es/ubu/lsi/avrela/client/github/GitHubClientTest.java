package es.ubu.lsi.avrela.client.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
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
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
              public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                out.value(value.toString());
              }
              @Override
              public ZonedDateTime read(JsonReader in) throws IOException {
                return ZonedDateTime.parse(in.nextString());
              }
            })
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
  public void shouldGetCommits(){
    // List<GitHubSprint> gitHubSprints = gitHubClient.findSprints(owner, repo);
    var commits = gitHubClient.findCommits(owner, repo, branch, null, LocalDateTime.now(), 1, 1);

    assertNotNull(commits, "Commit list must be none null.");
    assertTrue(commits.size()>0, "Commit list length must be greater than zero");
  }

  @Test
  public void shouldGetMilestones(){
    var sprints = gitHubClient.findMilestones(owner, repo, 1, 1);

    assertNotNull(sprints, "Sprint list must be none null.");
    assertTrue(sprints.size()>0, "Sprint list length must be greater than zero");
  }

  @Test
  public void shouldGetIssues(){
    var issues = gitHubClient.findIssuesByMilestone(owner, repo, milestone, 1 ,1);

    assertNotNull(issues, "Issue list must be none null.");
    assertTrue(issues.size()>0, "Issue list length must be greater than zero");
  }

  @Test
  public void sprintsInfoShouldBeComplete(){
    var sprints = gitHubClient.findMilestones(owner, repo, 1, 100);

    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getNumber() != null));
    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getTitle() != null));
    assertTrue(sprints.stream().anyMatch( sprint -> sprint.getState() != null));
  }

}