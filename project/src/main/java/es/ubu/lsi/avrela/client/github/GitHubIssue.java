package es.ubu.lsi.avrela.client.github;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubIssue {

  private Integer number;

  private GitHubMilestone milestone;

  private String title;

  private String body;

  private List<GitHubLabel> labels;

  private GitHubItemState state;

  private GitHubUser assignee;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  /**
   * Checks whether body contain task list.
   */
  public Boolean hasTaskList(){
    if (body == null ){
      return false;
    }
    return body.contains("[ ]") || body.contains("[x]");
  }

}
