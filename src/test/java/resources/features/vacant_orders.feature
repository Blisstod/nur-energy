Feature: Retrieving Vacant Orders for Drivers

  Scenario: Successfully retrieving a list of vacant orders
    Given an authenticated user with the role "TAXI_DRIVER"
    When they send a GET request to "/rest/taxi/order/vacant"
    Then the system returns status 200
    And a JSON list of all available orders for drivers
