package es.ubu.lsi.avrela.css.adapter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApmController {

  @GetMapping("/css-apm")
  public String index(){
    return "apm/index";
  }

}
