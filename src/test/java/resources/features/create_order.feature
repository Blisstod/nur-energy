Feature: Creating an Order

  Scenario: Successfully creating an order
    Given an authenticated user with a valid token
    When they send a POST request to "/rest/taxi/order" with new order details
    Then the system creates a new order
    And returns status 200 with a JSON response containing order details
    And drivers receive a notification about the new order
