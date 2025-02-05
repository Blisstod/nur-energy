package kz.nur.energy.steps;

import io.cucumber.java.en.*;
import kz.nur.energy.dto.OrderRequest;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CreateOrderSteps {
    private ResponseEntity<String> response;

    @Given("an authenticated user with a valid token")
    public void authenticatedUser() {
        // Assume token is generated for simplicity
        // Add logic to authenticate the user if required
    }

    @When("they send a POST request to {string} with new order details")
    public void sendCreateOrderRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        OrderRequest orderRequest = new OrderRequest(); // Populate order request
        response = restTemplate.postForEntity("http://localhost:8080" + url, orderRequest, String.class);
    }

    @Then("the system creates a new order")
    public void checkOrderCreated() {
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @And("returns status {int} with a JSON response containing order details")
    public void responseContainsOrderDetails(int statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCodeValue());
        Assertions.assertTrue(response.getBody().contains("orderId"));
    }
}
