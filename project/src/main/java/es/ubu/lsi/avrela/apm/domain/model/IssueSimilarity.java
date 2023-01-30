package es.ubu.lsi.avrela.apm.domain.model;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

public class IssueSimilarity {

  public enum Feature {
    LABELS,
    STATE,
    ISSUE_NAME
  }

  public static double calculate(Issue a, Issue b, EnumMap<Feature, Double> featureWeights) {
    // Calculate the intersection and union between the two sets of labels
    Set<String> labelIntersection = new HashSet<>(a.getLabels());
    labelIntersection.retainAll(b.getLabels());
    int labelIntersectionSize = labelIntersection.size();
    Set<String> labelUnion = new HashSet<>(a.getLabels());
    labelUnion.addAll(b.getLabels());
    int labelUnionSize = labelUnion.size();

    // Calculate the Jaccard similarity using the formula:
    // (size of intersection) / (size of union)
    double labelSimilarity = (double) labelIntersectionSize / labelUnionSize;

    int stateSimilarity = ( a.getState() == b.getState()) ? 1 : 0;

    // Calculate issue names similarity using Jaro-Winkler distance.
    double issueNameSimilarity = JaroWinklerDistance.getDistance(a.getName(), b.getName());

    return ((labelSimilarity*featureWeights.get(Feature.LABELS))
        +(stateSimilarity*featureWeights.get(Feature.STATE))
        +(issueNameSimilarity*featureWeights.get(Feature.ISSUE_NAME)) )/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();
  }
}
