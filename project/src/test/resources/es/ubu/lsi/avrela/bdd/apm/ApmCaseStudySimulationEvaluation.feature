Feature: Agile project management simulation case study evaluation

  Evaluation involves:
    - A case study
    - A simulation
    - A rubric

  Scenario: Successful evaluation
    Given a case study with repo owner "davidmigloz", name "go-bees" and time period 2017-01-25T00:00:00Z 2017-01-25T23:59:59Z
    And a simulation with repo owner "davidmigloz", name "go-bees", time period 2017-01-25T00:00:00Z 2017-01-25T23:59:59Z and 1 participant(s)
    And a rubric
      | Criteria                                         | 0  | 1           | 2    |
      | Valoración del trabajo en equipo en Github       | 50 | None        | 100  |
      | Aprendizaje de herramientas de gestión de tareas | 0  | 100         | 100  |
    When I apply the rubric
    Then rubric score should be
      | Criteria                                         | 0    | 1          | 2       |
      | Valoración del trabajo en equipo en Github       | X    | None       | None    |
      | Aprendizaje de herramientas de gestión de tareas | None | None       | X       |