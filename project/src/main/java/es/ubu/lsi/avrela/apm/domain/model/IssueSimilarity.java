package es.ubu.lsi.avrela.apm.domain.model;

import java.util.HashSet;
import java.util.Set;

public class IssueSimilarity {

  public static double calculate(Issue a, Issue b) {
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

    return labelSimilarity;
  }
}
