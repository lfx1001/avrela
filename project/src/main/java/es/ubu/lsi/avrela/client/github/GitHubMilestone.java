package es.ubu.lsi.avrela.client.github;

import lombok.Data;

@Data
public class GitHubMilestone {

  private String number;

  private String title;

  private GitHubItemState state;

}
