package es.ubu.lsi.avrela.client.github;

import java.io.Serializable;
import lombok.Data;

@Data
public class GitHubCommit implements Serializable {

  private String sha;

}
