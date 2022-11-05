Feature: Import

  Project management info should be imported when input data is valid.

  Scenario: Import successful with data
    Given repository can be fetched
    When I try to import the repository
    Then repository project management info should be accessed locally

