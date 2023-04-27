package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.css.port.ApmCssEvaluationService;
import es.ubu.lsi.avrela.css.util.ApmCssDataGenerator;
import es.ubu.lsi.avrela.css.util.ScmCssDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ScmController {

  @GetMapping("/scm-css")
  public String index(Model model){
    WebScmCaseStudySimulation result = ScmCssDataGenerator.getWebScmCaseStudySimulation();
    model.addAttribute("webScmCaseStudySimulation", result);
    return "pages/scm-css";
  }

  @PostMapping("/scm-css")
  public String create(@ModelAttribute WebApmCaseStudySimulation sim, Model model){
    ApmCssEvaluationService apmCssEvaluationService = new ApmCssEvaluationService();
    WebApmCaseStudySimulation aux = ApmCssDataGenerator.getWebApmCaseStudySimulation();
    WebApmCaseStudySimulation result = apmCssEvaluationService.evaluate(sim);
    model.addAttribute("webScmCaseStudySimulation", result);
    return "pages/scm-css";
  }

}