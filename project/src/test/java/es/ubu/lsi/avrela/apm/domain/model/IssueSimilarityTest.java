package es.ubu.lsi.avrela.apm.domain.model;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueSimilarityTest {

  @Test
  public void shouldBeTotallySimilar(){
    Issue a = getIssue();

    Issue b = a;

    double result = IssueSimilarity.calculate(a,b);

    Assertions.assertEquals(1.0, result, "Should be totally similar");

  }

  @Test
  public void shouldBeTotallyDifferent(){
    Issue a = getIssue();

    Issue b = new Issue.IssueBuilder()
        .id("xxx")
        .hasLink(true)
        .hasImages(true)
        .hasTaskList(true)
        .labels(List.of("label3", "label4"))
        .build();

    double result = IssueSimilarity.calculate(a,b);

    Assertions.assertEquals(0.0, result, "Should be totally different");

  }

  private static Issue getIssue() {
    return new Issue.IssueBuilder()
        .id("xxx")
        .hasLink(true)
        .hasImages(true)
        .hasTaskList(true)
        .labels(List.of("label1", "label2"))
        .build();
  }

}
