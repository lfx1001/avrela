package es.ubu.lsi.avrela.client.github;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class GitHubClientTest {

  /** Repository owner. */
  private final String owner = "davidmigloz";

  /** Repository. */
  private final String repo = "go-bees";

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
  public void shouldGetCommitsFromGitHub(){
    // List<GitHubSprint> gitHubSprints = gitHubClient.findSprints(owner, repo);
    List<GitHubCommit> commits = gitHubClient.findCommits(owner, repo, null, LocalDateTime.now(), 1, 1);

    assertNotNull(commits, "Commit list must be none null.");
    assertTrue(commits.size()>0, "Commit list length must be greater than zero");
  }

  @Test
  public void shouldGetLinkHeaderFromGitHubRequest(){
    Response response = gitHubClient.getFindCommitsResponse(owner, repo, null, LocalDateTime.now(), 1, 1);
    Collection<String> links = response.headers().get("link");

    assertNotNull(links, "Link header should be none null");
    assertEquals(1, links.size(), "Link header value should be unique");
  }

  @Test
  @Disabled("TODO: extract number of pages form response")
  public void shouldExtractNumberOfPagesFromGithubRequest(){
    // given
    String sampleLinkResponse= "<https://api.github.com/repositories/543627276/commits?until=2022-10-22T21%3A38%3A54.136242&page=2&per_page=1>; rel=\"next\", <https://api.github.com/repositories/543627276/commits?until=2022-10-22T21%3A38%3A54.136242&page=13&per_page=1>; rel=\"last\"";

    //TODO: implement extraction logic.
    Pattern pattern = Pattern.compile("page=(.*?)");
    //Pattern pattern = Pattern.compile("'(.*?)'");
    Matcher matcher = pattern.matcher(sampleLinkResponse);
    if (matcher.find())
    {
      System.out.println(matcher.group(0));
    }else{
      System.out.println("Not found");
    }
  }
}