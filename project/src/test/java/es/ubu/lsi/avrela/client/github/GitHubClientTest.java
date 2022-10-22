package es.ubu.lsi.avrela.client.github;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import feign.Feign;
import feign.Logger.Level;
import feign.Response;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GitHubClientTest {

  /** Repository owner. */
  private String owner = "lfx1001";

  /** Reporsitory. */
  private String repo = "avrela";

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

  //@Test
  public void shouldGetCommitsFromGitHub(){
    // List<GitHubSprint> gitHubSprints = gitHubClient.findSprints(owner, repo);
    List<GitHubCommit> commits = gitHubClient.findCommits(owner, repo, null, LocalDateTime.now(), 1, 1);

    assertNotNull(commits, "Commit list must not be null.");
    assertTrue(commits.size()>0, "Commit list length must be greater than zero");
  }

  @Test
  public void shouldGetTotalCommitsFromGitHub(){
    Response response = gitHubClient.countTotalCommits(owner, repo, null, LocalDateTime.now(), 1, 1);
    Collection<String> links = response.headers().get("link");
    links.stream().forEach( link -> System.out.println(link));
  }
}