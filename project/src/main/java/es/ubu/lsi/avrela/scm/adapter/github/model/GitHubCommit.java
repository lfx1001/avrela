package es.ubu.lsi.avrela.scm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GitHubCommit {

  private String sha;

  @SerializedName("commit")
  private GitHubCommitData data;

}
