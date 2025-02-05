package kz.nur.energy.steps;

import io.cucumber.java.en.*;
import kz.nur.energy.dto.LoginUserRequest;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LoginSteps {
    private String phone;
    private String password;
    private ResponseEntity<String> response;

    @Given("a user with phone number {string} and password {string} is registered in the system")
    public void userIsRegisteredInSystem(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    @When("they send a POST request to {string} with valid credentials")
    public void sendPostRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.postForEntity("http://localhost:8080" + url,
                new LoginUserRequest(phone, password), String.class);
    }

    @Then("the system returns status {int}")
    public void checkResponseStatus(int statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCodeValue());
    }

    @And("the response contains a JSON with an authentication token")
    public void responseContainsToken() {
        Assertions.assertTrue(response.getBody().contains("token"));
    }
}
