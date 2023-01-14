package es.ubu.lsi.avrela.apm.domain.model;

import es.ubu.lsi.avrela.apm.domain.model.IssueSimilarity.Feature;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IssueSimilarityTest {

  @Test
  public void shouldBeTotallySimilar(){
    Issue a = getIssue();

    Issue b = a;

    double result = IssueSimilarity.calculate(a,b, getFeatureWeights());

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
        .state(IssueState.CLOSED)
        .labels(List.of("label3", "label4"))
        .build();

    double result = IssueSimilarity.calculate(a,b,getFeatureWeights());

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

  private static EnumMap<IssueSimilarity.Feature, Double> getFeatureWeights() {
    EnumMap<IssueSimilarity.Feature, Double> result = new EnumMap<>(IssueSimilarity.Feature.class);
    result.put(Feature.LABELS, 1.0);
    result.put(Feature.STATE, 1.0);
    return result;
  }

}
