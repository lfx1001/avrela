package es.ubu.lsi.avrela.client.github;

import com.google.gson.annotations.SerializedName;
import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class GitHubComment {

  private String id;

  private String body;

  private GitHubUser user;

  @SerializedName("created_at")
  private ZonedDateTime createdAt;

  @SerializedName("updated_at")
  private ZonedDateTime updatedAt;

}
