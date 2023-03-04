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
            .build())
        .simulation( WebHistoricalApmData.builder()
            .repoOwner("davidmigloz")
            .repoName("go-bees")
            .build())
        .build();
    System.out.println(result.getCaseStudy().getRepoOwner());
    model.addAttribute("webApmCaseStudySimulation", result);
    return "apm/index";
  }

  @PostMapping("/css-apm")
  public String create(@ModelAttribute WebApmCaseStudySimulation sim, Model model){
    model.addAttribute("webApmCaseStudySimulation", sim);
    return "apm/index";
  }

}
