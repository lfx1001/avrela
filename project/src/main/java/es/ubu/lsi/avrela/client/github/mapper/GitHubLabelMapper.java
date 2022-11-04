package es.ubu.lsi.avrela.client.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubLabel;
import java.util.ArrayList;
import java.util.List;

public class GitHubLabelMapper {

  public String toDomain(GitHubLabel label){
    return label.getName();
  }

  public List<String> toDomain(List<GitHubLabel> gitHubLabels){
    if (gitHubLabels == null){
      return null;
    }
    List<String> result = new ArrayList<>(gitHubLabels.size());
    for (GitHubLabel label : gitHubLabels) {
      result.add(toDomain(label));
    }
    return result;
  }

}
