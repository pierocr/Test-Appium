Feature: Login en App Móvil

  Scenario: Login exitoso
    Given que la aplicación está instalada
    When inicio la app e ingreso credenciales válidas
    Then debo ver la pantalla principal
