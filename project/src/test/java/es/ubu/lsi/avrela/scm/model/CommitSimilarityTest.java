package es.ubu.lsi.avrela.scm.model;

import java.util.ArrayList;
import java.util.List;

public class CommitSimilarityTest {



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
