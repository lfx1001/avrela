package es.ubu.lsi.avrela.client.github;

import lombok.Data;

@Data
public class GitHubCommitData {

  private String message;

  private GitHubCommitAuthor author;

}
