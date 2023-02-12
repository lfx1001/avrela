package es.ubu.lsi.avrela.scm.model;

import es.ubu.lsi.avrela.similarity.Jaccard;
import es.ubu.lsi.avrela.similarity.JaroWinklerDistance;
import java.util.EnumMap;
import java.util.HashSet;

public class CommitSimilarity {

  public enum Feature {
    FILES,
    MESSAGE
  }

  public static double similarityOf(
      Commit a, Commit b, EnumMap<CommitSimilarity.Feature, Double> featureWeights) {

    // Commit files similarity using commit file names
    double commitFilesSimilarity = Jaccard.similarityOf(new HashSet<>(a.getFiles()), new HashSet<>(b.getFiles()));


    // Calculate issue names similarity using Jaro-Winkler distance.
    double messageSimilarity = JaroWinklerDistance.getDistance(a.getMessage(), b.getMessage());

    return ((commitFilesSimilarity*featureWeights.get(CommitSimilarity.Feature.FILES))
        +(messageSimilarity*featureWeights.get(Feature.MESSAGE)))/featureWeights.values().stream().mapToDouble(Double::doubleValue).sum();
  }

}
