Feature: Import agile project management information

  Agile project management information should be imported when input data is valid.
  Uses https://github.com/davidmigloz/go-bees as example data.

  Scenario: Import successful
    Given a repository owned by "davidmigloz" named "go-bees"
    And the sprint dates 2017-01-25T00:00:00Z and 2017-01-25T23:59:59Z
    When I import the sprint issues
    Then total issues should be 17
    And total issues labeled as documentation should be 7
    And total issues labeled as feature should be 4
    And total issues labeled as testing should be 2
    And total issues labeled as bug should be 4
    And total issues with comments should be 2
    And total closed issues should be 17



