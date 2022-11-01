package es.ubu.lsi.avrela.bdd;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("es/ubu/lsi/avrela/bdd")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "es.ubu.lsi.avrela.bdd")
public class CucumberConfig {

}
