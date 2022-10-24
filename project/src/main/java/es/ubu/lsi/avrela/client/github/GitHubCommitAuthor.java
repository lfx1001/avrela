package es.ubu.lsi.avrela.client.github;

import java.time.ZonedDateTime;
import lombok.Data;

@Data
public class GitHubCommitAuthor {

  private String name;

  private String email;

  private ZonedDateTime date;

}
