package es.ubu.lsi.avrela.css.util;

import java.util.HashMap;
import java.util.Map;

public class RubricDataGenerator {

  public void apmCriteria(){
    // teamwork criteria
    Map<String, String> teamWorkCriteriaDefinition = new HashMap<>();
    teamWorkCriteriaDefinition.put("0", "50");
    teamWorkCriteriaDefinition.put("1", "None");
    teamWorkCriteriaDefinition.put("2", "100");
    // Task management tool - description
    Map<String, String> ttlDescriptionCriteriaDefinition = new HashMap<>();
    ttlDescriptionCriteriaDefinition.put("0", "0");
    ttlDescriptionCriteriaDefinition.put("1", "100");
    ttlDescriptionCriteriaDefinition.put("2", "None");
    // Task management tool - organization
    Map<String, String> ttlOrganizationCriteriaDefinition = new HashMap<>();
    ttlOrganizationCriteriaDefinition.put("0", "0");
    ttlOrganizationCriteriaDefinition.put("1", "100");
    ttlOrganizationCriteriaDefinition.put("2", "None");


  }

}
