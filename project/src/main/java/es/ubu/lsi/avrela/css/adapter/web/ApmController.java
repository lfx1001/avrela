package es.ubu.lsi.avrela.css.adapter.web;

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
    model.addAttribute("webApmCaseStudySimulation", new WebApmCaseStudySimulation());
    return "apm/index";
  }

  @PostMapping("/css-apm")
  public String create(@ModelAttribute WebApmCaseStudySimulation sim, Model model){
    model.addAttribute("webApmCaseStudySimulation", new WebApmCaseStudySimulation());
    return "apm/index";
  }

}
