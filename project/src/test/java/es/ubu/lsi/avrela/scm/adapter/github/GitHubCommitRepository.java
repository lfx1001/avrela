package es.ubu.lsi.avrela.scm.adapter.github;

import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.adapter.github.model.GitHubCommit;
import es.ubu.lsi.avrela.scm.domain.model.Commit;
import es.ubu.lsi.avrela.scm.port.CommitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GitHubCommitRepository implements CommitRepository {

  private final GitHubScmClient gitHubScmClient;

  private final GitHubCommitMapper commitMapper;

  @Override
  public Commit findCommit(String owner, String repo, String sha) {
    log.info("Finding out a commit with coordinates [{}]", owner + " " + repo + " " + sha);
    GitHubCommit gitHubCommit = gitHubScmClient.findCommit(owner, repo, sha);
    Commit result = commitMapper.toDomain(gitHubCommit);
    return result;
  }
}
