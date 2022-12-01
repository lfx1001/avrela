Feature: Agile project management simulation case study evaluation

  Evaluation involves:
    - A case study
    - A simulation
    - A rubric

  Scenario: Successful evaluation
    Given a case study with repo owner "repoOwner", name "repoName" and time period "2021-04-01" "2021-04-02"
    And a simulation with repo owner "repoOwner", name "repoName" and time period "2021-04-01" "2021-04-02"
    And a rubric
    When I apply the rubric
      | Criteria                                         | 0  | 1     | 2 |
      | Valoración del trabajo en equipo en Github       | 50 | None  |   |
      | Aprendizaje de herramientas de gestión de tareas | 100|       |   |
    Then rubric score should be