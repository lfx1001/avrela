package es.ubu.lsi.avrela.client.github;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import lombok.Data;

@Data
public class GitHubIssue {

  private Integer number;

  private GitHubMilestone milestone;

  private String title;

  private String body;

  private Collection<GitHubLabel> labels;

  private GitHubItemState state;

  private GitHubUser assignee;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

}
