Feature: Import agile project management information

  Agile project management info should be imported when input data is valid.

  Scenario: Import successful
    Given a repository owned by "davidmigloz" named "go-bees"
    And the dates 2017-01-26T00:00:00Z and 2017-01-26T23:59:59Z
    When I import the agile project management info
    Then agile project management should match expected

