package es.ubu.lsi.avrela.scm.adapter.github.mapper;

import es.ubu.lsi.avrela.scm.adapter.github.model.GitHubCommit;
import es.ubu.lsi.avrela.scm.domain.model.Commit;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubCommitMapper {

  private final GitHubCommitFileMapper commitFileMapper;

  public Commit toDomain(GitHubCommit commit){
    Commit result = Commit.builder()
        .sha(commit.getSha())
        .message(commit.getData().getMessage())
        .author(commit.getData().getAuthor().getName())
        .date(commit.getData().getAuthor().getDate())
        .files(commitFileMapper.toDomain(commit.getFiles()))
        .build();
    return result;
  }

}
