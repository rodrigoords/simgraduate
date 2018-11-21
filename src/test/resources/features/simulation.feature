Feature: I want to test simulations resource

  Background:
    Given Request with Authorization Header:
      | headerName    | headerValue                        |
      | Authorization | Basic c2ltZ3JhZHVhdGU6YnJhc2lsSHVl |

  Scenario: I want to create a simulation on data base
    Given I have only the following rows in the "courses" table:
      | id | name                     |
      | 1  | Engenharia da computação |
    And I have only the following rows in the "subjects" table:
      | id | name                          | observation                                       |
      | 1  | Sistemas Distribuidos         | Aborda os desafios da comunicação entre sistemas. |
      | 2  | Computação de alto desempenho | Entenda as threads e os super computadores        |
    When I make a POST call to "/simulations" endpoint with post body:
    """
    {
      "name": "Rodrigo Sene",
      "email": "rodrigo.sene@qisi.com.br",
      "cep": "13178183",
      "course": { "id": 1 },
      "subjects": [{ "id": 2 }]
    }
    """
    Then response status code should be 201
    And I should have the following rows in any order in the "simulations" table:
      | id | name         | email                    | cep      | course_id |
      | 1  | Rodrigo Sene | rodrigo.sene@qisi.com.br | 13178183 | 1         |
    And I should have the following rows in the "simulations_subjects" table:
      | id_simulation | id_subject |
      | 1             | 2          |