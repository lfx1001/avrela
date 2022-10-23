package es.ubu.lsi.avrela.client.github;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Data;

@Data
public class GitHubIssue {

  private Integer number;

  private String title;

  private String body;

  private Collection<GitHubLabel> labels;

  private GitHubItemState state;

}
