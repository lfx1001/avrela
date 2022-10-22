package es.ubu.lsi.avrela.client.github;


import feign.Param;
import feign.RequestLine;
import feign.Response;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Feign based GitHub client.
 *
 * @see https://www.baeldung.com/intro-to-feign
 */
public interface GitHubClient {

  /**
   * Find commits.
   *
   * @param owner
   * @param repo
   * @param since
   * @param until
   * @param page
   * @param pageSize
   * @return
   * @see https://docs.github.com/en/rest/commits/commits#list-commits
   * @see https://docs.github.com/en/enterprise-cloud@latest/rest/guides/traversing-with-pagination
   */
  @RequestLine("GET /repos/{owner}/{repo}/commits?since={since}&until={until}&page={page}&per_page={pageSize}")
  List<GitHubCommit> findCommits(@Param("owner") String owner, @Param("repo") String repo,
      @Param("since") LocalDateTime since, @Param("until") LocalDateTime until,
      @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  @RequestLine("GET /repos/{owner}/{repo}/commits?since={since}&until={until}&page={page}&per_page={pageSize}")
  Response countTotalCommits(@Param("owner") String owner, @Param("repo") String repo,
      @Param("since") LocalDateTime since, @Param("until") LocalDateTime until,
      @Param("page") Integer page, @Param("pageSize") Integer pageSize);

  @RequestLine("GET /repos/{owner}/{repo}/milestones?state=all")
  List<GitHubSprint> findSprints(@Param("owner") String owner, @Param("repo") String repo);


}
