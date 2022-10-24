package es.ubu.lsi.avrela.client.github;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class GitHubMilestone {

  private String number;

  private String title;

  private GitHubItemState state;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  @SerializedName("updated_at")
  private ZonedDateTime updatedAt;

  @SerializedName("due_on")
  private ZonedDateTime dueOn;

  @SerializedName("closed_at")
  private ZonedDateTime closedAt;

}
