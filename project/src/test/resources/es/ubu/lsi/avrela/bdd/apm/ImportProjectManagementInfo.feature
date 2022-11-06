Feature: Import agile project management information

  Agile project management information should be imported when input data is valid.
  Uses https://github.com/davidmigloz/go-bees/milestone/17 (due on 2017-01-25T08:00:00Z) as sample data.

  Scenario: Import successful
    Given a repository owned by "davidmigloz" named "go-bees"
    And the dates 2017-01-25T00:00:00Z and 2017-01-25T23:59:59Z
    When I import the agile project management info
    Then agile project management should match expected

