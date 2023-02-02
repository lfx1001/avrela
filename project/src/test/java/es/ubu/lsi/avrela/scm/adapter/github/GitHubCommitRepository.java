package es.ubu.lsi.avrela.scm.adapter.github;

import es.ubu.lsi.avrela.scm.adapter.github.mapper.GitHubCommitMapper;
import es.ubu.lsi.avrela.scm.adapter.github.model.GitHubCommit;
import es.ubu.lsi.avrela.scm.domain.model.Commit;
import es.ubu.lsi.avrela.scm.port.CommitRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubCommitRepository implements CommitRepository {

  private final GitHubScmClient gitHubScmClient;

  private final GitHubCommitMapper commitMapper;

  @Override
  public Commit findCommitBySha(String owner, String repo, String sha) {
    GitHubCommit gitHubCommit = gitHubScmClient.findCommitBySha(owner, repo, sha);
    Commit result = commitMapper.toDomain(gitHubCommit);
    return result;
  }
}
