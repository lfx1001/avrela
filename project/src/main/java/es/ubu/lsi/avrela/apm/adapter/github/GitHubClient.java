package es.ubu.lsi.avrela.apm.adapter.github;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubComment;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubMilestone;
import es.ubu.lsi.avrela.scm.adapter.github.model.GitHubCommit;
import feign.Feign;
import feign.Logger.Level;
import feign.Param;
import feign.RequestLine;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.slf4j.Slf4jLogger;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Feign based GitHub client.
 *
 * @see <a href="https://github.com/OpenFeign/feign">Official documentation</a>
 * @see <a href="https://www.baeldung.com/intro-to-feign">Intro to Feign</a>
 */
public interface GitHubClient {

  static GitHubClient with(Level loggerLevel) {
    final Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
            .serializeNulls()
            .create();
    final Decoder decoder = new GsonDecoder(gson);

    final GitHubAuthenticationInterceptor authInterceptor = new GitHubAuthenticationInterceptor(System.getenv("GITHUB_TOKEN"));

    GitHubClient result = Feign.builder()
        .requestInterceptor(authInterceptor)
        .logger(new Slf4jLogger(GitHubClient.class))
        .encoder(new GsonEncoder())
        .decoder(decoder)
        .logLevel(loggerLevel)
        .target(GitHubClient.class, "https://api.github.com");

    return result;
  }

  /**
   * Find commits.
   *
   * @param owner
   * @param repo
   * @param branch
   * @param since
   * @param until
   * @param page
   * @param pageSize
   * @return
   * @see <a href="https://docs.github.com/en/rest/commits/commits#list-commits">List commits API</a>
   * @see <a href="https://docs.github.com/en/enterprise-cloud@latest/rest/guides/traversing-with-pagination">Pagination guidelines</a>
   */
  @RequestLine("GET /repos/{owner}/{repo}/commits?sha={branch}&since={since}&until={until}&page={page}&per_page={pageSize}")
  List<GitHubCommit> findCommits(@Param("owner") String owner, @Param("repo") String repo, @Param("branch") String branch,
      @Param("since") LocalDateTime since, @Param("until") LocalDateTime until,
      @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  /**
   * Find all milestones/sprints. Results are sorted by due_on field in ascending order.
   * Notice that this API operation does not accept due_on filtering.
   * @param owner
   * @param repo
   * @param page
   * @param pageSize
   * @return
   * @see <a href="https://docs.github.com/en/rest/issues/milestones">GitHub Milestones REST API</a>
   */
  @RequestLine("GET /repos/{owner}/{repo}/milestones?state=all&page={page}&per_page={pageSize}")
  List<GitHubMilestone> findMilestones(@Param("owner") String owner, @Param("repo") String repo, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  /**
   * Find issues by milestone. Results are sorted by creation time.
   * @param owner
   * @param repo
   * @param milestone
   * @param page
   * @param pageSize
   * @return
   * @see <a href="https://docs.github.com/en/rest/issues/issues#list-repository-issues">GitHub Issues REST API</a>
   */
  @RequestLine("GET /repos/{owner}/{repo}/issues?milestone={milestone}&state=all&page={page}&per_page={pageSize}")
  List<GitHubIssue> findIssuesByMilestone(@Param("owner") String owner, @Param("repo") String repo,@Param("milestone") Integer milestone, @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  /**
   * Find issue commments.
   * @param owner
   * @param repo
   * @param issue
   * @return
   */
  @RequestLine("GET /repos/{owner}/{repo}/issues/{issue}/comments")
  List<GitHubComment> findCommentsByIssue(@Param("owner") String owner, @Param("repo") String repo, @Param("issue") String issue);

}
