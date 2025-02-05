Feature: User Authentication

  Scenario: Successful login
    Given a user with phone number "77071112233" and password "password123" is registered in the system
    When they send a POST request to "/rest/login" with valid credentials
    Then the system returns status 200
    And the response contains a JSON with an authentication token

  Scenario: Failed login (incorrect password)
    Given a user with phone number "77071112233" is registered in the system
    When they send a POST request to "/rest/login" with an incorrect password "wrongpass"
    Then the system returns status 400
    And the response contains the message "Incorrect password"
