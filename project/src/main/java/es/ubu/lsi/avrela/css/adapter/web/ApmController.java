package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import es.ubu.lsi.avrela.apm.model.Issue;
import es.ubu.lsi.avrela.apm.model.IssueState;
import es.ubu.lsi.avrela.apm.model.Sprint;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ApmController {

  @GetMapping("/apm-css")
  public String index(Model model){
    WebApmCaseStudySimulation result = WebApmCaseStudySimulation.builder()
        .caseStudy( WebHistoricalApmData.builder()
            .repoOwner("davidmigloz")
            .repoName("go-bees")
            .stringifyStartAt("2017-01-25")
            .stringifyEndAt("2017-01-25")
            .sprints(
                List.of(
                    Sprint.builder()
                        .issues(
                            List.of(
                                Issue.builder()
                                    .id("Id")
                                    .name("Issue Name")
                                    .state(IssueState.OPEN)
                                    .body("Loren ipsum, loren ipsum, loren ipsum, loren ipsum ...")
                                    .labels(
                                        List.of(
                                            "label1",
                                            "label2",
                                            "label3"
                                        )
                                    )
                                    .hasImages(Boolean.TRUE)
                                    .hasLink(Boolean.TRUE)
                                    .hasTaskList(Boolean.TRUE)
                                    .build()
                            )
                        )
                        .build()
                )
            )
            .build())
        .simulation( WebHistoricalApmData.builder()
            .repoOwner("davidmigloz")
            .repoName("go-bees")
            .stringifyStartAt("2017-01-25")
            .stringifyEndAt("2017-01-25")
            .build())
        .participants(1)
        .issueSimilarityFunctionConfig(
            WebIssueSimilarityFunctionConfig.builder()
                .labelWeight(1.0)
                .stateWeight(1.0)
                .issueNameWeight(1.0)
                .build()
        )
        .rubricEvaluation(
          WebRubricEvaluation.builder()
              .teamWork( new WebRubricCriteriaEvaluation( 100d, 2))
              .ttlDescription( new WebRubricCriteriaEvaluation( 100d, 1))
              .ttlOrganization( new WebRubricCriteriaEvaluation( 100d, 1))
              .build()
        )
        .build();
    model.addAttribute("webApmCaseStudySimulation", result);
    return "pages/apm-css";
  }

  @PostMapping("/apm-css")
  public String create(@ModelAttribute WebApmCaseStudySimulation sim, Model model){
    model.addAttribute("webApmCaseStudySimulation", sim);
    return "pages/apm-css";
  }

}
