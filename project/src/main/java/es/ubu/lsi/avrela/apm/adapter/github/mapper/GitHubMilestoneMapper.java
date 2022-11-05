package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubMilestone;
import es.ubu.lsi.avrela.apm.domain.model.Sprint;
import es.ubu.lsi.avrela.apm.domain.model.SprintState;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GitHubMilestoneMapper {

  private final GitHubIssueMapper issueMapper;


  public Sprint toDomain(GitHubMilestone milestone) {
    if (milestone == null){
      return null;
    }
    Sprint result = Sprint.builder()
        .id(milestone.getNumber().toString())
        .title(milestone.getTitle())
        .description(milestone.getDescription())
        .state(SprintState.valueOf(milestone.getState().name()))
        .dueOn(milestone.getDueOn())
        .issues(issueMapper.toDomain(milestone.getIssues()))
        .build();

    return result;
  }

}
