package es.ubu.lsi.avrela.client.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import feign.Feign;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import java.time.LocalDateTime;
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
    gitHubClient = Feign.builder()
        .logger(new Slf4jLogger(GitHubClient.class))
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
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

}