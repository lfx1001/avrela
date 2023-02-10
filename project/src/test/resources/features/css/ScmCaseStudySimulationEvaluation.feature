Feature: Source Control Management simulation case study evaluation

  Evaluation involves:
    - A case study
    - A simulation
    - A rubric

  Scenario: SCM successful evaluation
    Given a scm case study with repo owner "davidmigloz", name "go-bees", branch is "master" and time period 2017-01-25T00:00:00Z 2017-01-25T23:59:59Z
    And a scm simulation with repo owner "davidmigloz", name "go-bees", branch is "master" and time period 2017-01-25T00:00:00Z 2017-01-25T23:59:59Z and 1 participant(s)
    And a SCM evaluation rubric
      | Criteria                                         | 0  | 1           | 2    |
      | Teamwork                                         | 50 | None        | 100  |
    When I apply the SCM evaluation rubric
    Then SCM evaluation rubric score should be
      | Criteria                                         | 0    | 1          | 2       |
      | Teamwork                                         | None | None       | X       |
