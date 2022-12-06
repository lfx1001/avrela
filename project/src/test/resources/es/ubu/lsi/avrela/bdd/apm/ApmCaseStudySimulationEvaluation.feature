Feature: Agile project management simulation case study evaluation

  Evaluation involves:
    - A case study
    - A simulation
    - A rubric

  Scenario: Successful evaluation
    Given a case study with repo owner "repoOwner", name "repoName" and time period 2021-04-01T08:00:00Z 2021-04-02T08:00:00Z
    And a simulation with repo owner "repoOwner", name "repoName" and time period 2021-04-01T08:00:00Z 2021-04-02T08:00:00Z
    And a rubric
      | Criteria                                         | 0  | 1           | 2    |
      | Valoración del trabajo en equipo en Github       | 50 | None        | 100  |
      | Aprendizaje de herramientas de gestión de tareas | 0  | 100         | 100  |
    When I apply the rubric
    Then rubric score should be
      | Criteria                                         | 0    | 1          | 2       |
      | Valoración del trabajo en equipo en Github       | X    | None       | None    |
      | Aprendizaje de herramientas de gestión de tareas | None | None       | X       |