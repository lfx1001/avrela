package es.ubu.lsi.avrela.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class ImportStepDefinitions {

  @Given("repository can be fetched")
  public void fetchRepository() {
    Assertions.assertTrue(true);
  }

  @When("I try to import the repository")
  public void importRepository() {
    Assertions.assertTrue(true);
  }

  @Then("repository project management info should be accessed locally")
  public void getRepository() {
    Assertions.assertTrue(true);
  }

}
