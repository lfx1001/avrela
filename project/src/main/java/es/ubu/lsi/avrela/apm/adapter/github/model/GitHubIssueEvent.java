package es.ubu.lsi.avrela.apm.adapter.github.model;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Models a GitHub issue event
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssueEvent {

  private Long id;

  @SerializedName("event")
  private GitHubIssueEventType type;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  /**
   * Comment body for comment events.
   */
  private String body;

  /**
   * Only applies for reference events.
   */
  @SerializedName("commit_id")
  private String commitId;

}
