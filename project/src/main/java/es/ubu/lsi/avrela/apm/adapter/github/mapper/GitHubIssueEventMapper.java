package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssueEvent;
import es.ubu.lsi.avrela.apm.domain.model.IssueEvent;
import es.ubu.lsi.avrela.apm.domain.model.IssueEventType;
import java.util.ArrayList;
import java.util.List;

public class GitHubIssueEventMapper {

  public IssueEvent toDomain(GitHubIssueEvent event){
    if (event == null) {return null;}
    return IssueEvent.builder()
        .id(event.getId() == null ? null : event.getId().toString())
        .createdAt(event.getCreatedAt())
        .commitId(event.getCommitId())
        .body(event.getBody())
        .eventType(IssueEventType.valueOf(event.getType().name()))
        .build();
  }

  public List<IssueEvent> toDomain(List<GitHubIssueEvent> events) {
    if(events == null){
      return new ArrayList<>();
    }
    List<IssueEvent> result = new ArrayList<>(events.size());
    for (GitHubIssueEvent event : events){
      result.add(toDomain(event));
    }
    return result;
  }
}