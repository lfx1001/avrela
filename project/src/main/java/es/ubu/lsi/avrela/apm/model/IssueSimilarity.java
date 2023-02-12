package es.ubu.lsi.avrela.apm.model;

import es.ubu.lsi.avrela.similarity.Jaccard;
import es.ubu.lsi.avrela.similarity.JaroWinklerDistance;
import java.util.EnumMap;
import java.util.HashSet;

public class IssueSimilarity {

  public enum Feature {
    LABELS,
    STATE,
    ISSUE_NAME
  }

  public static double calculate(Issue a, Issue b, EnumMap<Feature, Double> featureWeights) {
    double labelSimilarity = Jaccard.similarityOf(new HashSet<>(a.getLabels()), new HashSet<>(b.getLabels()));

    int stateSimilarity = ( a.getState() == b.getState()) ? 1 : 0;

    // Calculate issue names similarity using Jaro-Winkler distance.
    double issueNameSimilarity = JaroWinklerDistance.getDistance(a.getName(), b.getName());

    return ((labelSimilarity*featureWeights.get(Feature.LABELS))
        +(stateSimilarity*featureWeights.get(Feature.STATE))
        +(issueNameSimilarity*featureWeights.get(Feature.ISSUE_NAME)) )/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();
  }
}
