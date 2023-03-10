package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.apm.adapter.web.WebHistoricalApmData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ApmController {

  @GetMapping("/css-apm")
  public String index(Model model){
    WebApmCaseStudySimulation result = WebApmCaseStudySimulation.builder()
        .caseStudy( WebHistoricalApmData.builder()
            .repoOwner("davidmigloz")
            .repoName("go-bees")
            .stringifyStartAt("2017-01-25")
            .stringifyEndAt("2017-01-25")
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
              .ttlOrganization( new WebRubricCriteriaEvaluation( 100d, 2))
              .build()
        )
        .build();
    model.addAttribute("webApmCaseStudySimulation", result);
    return "apm/index";
  }

  @PostMapping("/css-apm")
  public String create(@ModelAttribute WebApmCaseStudySimulation sim, Model model){
    model.addAttribute("webApmCaseStudySimulation", sim);
    return "apm/index";
  }

}
