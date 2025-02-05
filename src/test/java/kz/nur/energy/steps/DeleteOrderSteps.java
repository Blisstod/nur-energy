package kz.nur.energy.steps;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DeleteOrderSteps {
    private ResponseEntity<String> response;
    private String orderId;

    @Given("an authenticated user with a valid token and an order with ID {string}")
    public void authenticatedUserAndOrder(String orderId) {
        this.orderId = orderId;
    }

    @When("they send a DELETE request to {string}")
    public void sendDeleteRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.exchange("http://localhost:8080" + url + "/" + orderId,
                HttpMethod.DELETE, null, String.class);
    }

    @Then("the system deletes the order")
    public void checkOrderDeleted() {
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @And("returns status {int} with a confirmation message")
    public void checkConfirmationMessage(int statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCodeValue());
        Assertions.assertTrue(response.getBody().contains("Order deleted successfully"));
    }
}
