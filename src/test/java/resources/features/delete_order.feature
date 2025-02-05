Feature: Deleting an Order

  Scenario: Successfully deleting an order
    Given an authenticated user with a valid token and an order with ID "123e4567-e89b-12d3-a456-426614174000"
    When they send a DELETE request to "/rest/taxi/order/delete/123e4567-e89b-12d3-a456-426614174000"
    Then the system deletes the order
    And returns status 200 with a confirmation message
