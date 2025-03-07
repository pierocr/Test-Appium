Feature: Inicio de sesión en la aplicación

  Background:
    Given que la aplicación está instalada

  Scenario Outline: Intento de inicio de sesión con diferentes credenciales
    When inicio la app e ingreso usuario "<usuario>" y contraseña "<pass>"
    Then el resultado del login debe ser "<resultado>"

    Examples:
      | usuario         | pass     | resultado              |
      | standard_user   | secret_sauce  | éxito                  |
      | locked_out_user | secret_sauce  | usuario bloqueado      |
      | problem_user    | secret_sauce  | éxito                  |
      | standard_user   | incorrecto    | credenciales inválidas |
      |                | secret_sauce  | campo usuario vacío    |
      | standard_user   |               | campo contraseña vacío |
