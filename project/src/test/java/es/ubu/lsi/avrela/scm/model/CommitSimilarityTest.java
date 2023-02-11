package es.ubu.lsi.avrela.scm.model;

import es.ubu.lsi.avrela.similarity.Jaccard;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommitSimilarityTest {

  @Test
 public void jaccardSimilarityTest(){
   Assertions.assertEquals(0.2, Jaccard.similarityOf( new HashSet<>(commitFiles12345()),
       new HashSet<>(commitFiles1())));
   Assertions.assertEquals(0.8, Jaccard.similarityOf( new HashSet<>(commitFiles1234()),
       new HashSet<>(commitFiles12345())));
   Assertions.assertEquals(0.0, Jaccard.similarityOf( null,
       new HashSet<>(commitFiles12345())));
   Assertions.assertEquals(0.0, Jaccard.similarityOf( new HashSet<>(commitFiles9()),
       new HashSet<>(commitFiles12345())));
   Assertions.assertEquals(1.0, Jaccard.similarityOf( new HashSet<>(commitFiles12345()),
       new HashSet<>(commitFiles12345())));
 }

 public void commitSimilarity () {
   Commit commitA = Commit.builder()
       .message("ABCDE")
       .files(commitFiles12345())
       .build();
   Commit commitB = Commit.builder()
       .message("ABCDEFG")
       .files(commitFiles12345())
       .build();
 }

  private  List<CommitFile> commitFiles12345() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    result.add(CommitFile.builder().name("2").build());
    result.add(CommitFile.builder().name("3").build());
    result.add(CommitFile.builder().name("4").build());
    result.add(CommitFile.builder().name("5").build());
    return result;
  }

  private  List<CommitFile> commitFiles1234() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    result.add(CommitFile.builder().name("2").build());
    result.add(CommitFile.builder().name("3").build());
    result.add(CommitFile.builder().name("4").build());
    return result;
  }

  private  List<CommitFile> commitFiles1() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("1").build());
    return result;
  }

  private  List<CommitFile> commitFiles9() {
    List<CommitFile> result = new ArrayList<>();
    result.add(CommitFile.builder().name("9").build());
    return result;
  }
    
 
  
}
