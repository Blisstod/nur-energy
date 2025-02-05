package kz.nur.energy.steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class VacantOrdersSteps {
    private ResponseEntity<List> response;

    @Given("an authenticated user with the role {string}")
    public void authenticatedUserWithRole(String role) {
        // Assume role verification for simplicity
    }

    @When("they send a GET request to {string}")
    public void sendGetRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.getForEntity("http://localhost:8080" + url, List.class);
    }

    @Then("the system returns status {int}")
    public void checkResponseStatus(int statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    }

    @And("a JSON list of all available orders for drivers")
    public void checkVacantOrdersList() {
        Assertions.assertFalse(response.getBody().isEmpty());
    }
}
