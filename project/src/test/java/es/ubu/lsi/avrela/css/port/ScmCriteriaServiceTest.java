package es.ubu.lsi.avrela.css.port;

import es.ubu.lsi.avrela.scm.model.Commit;
import es.ubu.lsi.avrela.scm.model.HistoricalScmData;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScmCriteriaServiceTest {

  private final static ScmCriteriaService scmCriteriaService = new ScmCriteriaService();

  @Test
  void whenAllCommitsAlternativeAnd2ParticipantsShouldReturnAllCommits() {
    HistoricalScmData scmData = HistoricalScmData.builder()
        .commits(List.of(
            getCommitWithAuthor("A"),
            getCommitWithAuthor("B"),
            getCommitWithAuthor("A"),
            getCommitWithAuthor("B"),
            getCommitWithAuthor("A"),
            getCommitWithAuthor("B")

        ))
        .build();

    Integer result = scmCriteriaService.teamWorkEvaluationBasedOnAlternativeCommits(scmData, 2);

    Assertions.assertEquals(6, result);
  }

  @Test
  void whenAllCommitsAlternativeAnd3ParticipantsShouldReturnAllCommits() {
    HistoricalScmData scmData = HistoricalScmData.builder()
        .commits(List.of(
            getCommitWithAuthor("A"),
            getCommitWithAuthor("B"),
            getCommitWithAuthor("C"),
            getCommitWithAuthor("A"),
            getCommitWithAuthor("B"),
            getCommitWithAuthor("C")

        ))
        .build();

    Integer result = scmCriteriaService.teamWorkEvaluationBasedOnAlternativeCommits(scmData, 3);

    Assertions.assertEquals(6, result);
  }

  private static Commit getCommitWithAuthor(String author) {
    return Commit.builder()
        .author(author)
        .build();
  }
}