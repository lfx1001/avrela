package es.ubu.lsi.avrela.client.github;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GitHubCommit {

  private String sha;

  @SerializedName("commit")
  private GitHubCommitData data;

}
