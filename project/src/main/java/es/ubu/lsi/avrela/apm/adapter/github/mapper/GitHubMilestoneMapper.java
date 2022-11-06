package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubMilestone;
import es.ubu.lsi.avrela.apm.domain.model.Sprint;
import es.ubu.lsi.avrela.apm.domain.model.SprintState;
import java.util.ArrayList;
import java.util.List;
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

  public List<Sprint> toDomain(List<GitHubMilestone> milestones) {
    if(milestones == null){
      return null;
    }
    List<Sprint> result = new ArrayList<>(milestones.size());
    for (GitHubMilestone milestone : milestones){
      result.add(toDomain(milestone));
    }
    return result;
  }
}
