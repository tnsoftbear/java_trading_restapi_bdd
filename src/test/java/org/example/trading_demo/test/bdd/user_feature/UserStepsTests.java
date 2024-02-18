package org.example.trading_demo.test.bdd.user_feature;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.example.trading_demo.model.User;
import org.example.trading_demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class UserStepsTests {

    private final RestTemplate restTemplate = new RestTemplate();
    private User resultUser;
    @Autowired
    private UserService userService;
    @Value("${api.url}")
    private String urlBase;

    @Given("user with username {string} does not exist")
    public void user_with_username_does_not_exist(String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            throw new AssertionError("User already exists with username: " + username);
        }
    }

    @Given("user with username {string} exists")
    public void user_with_username_exists(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new AssertionError("User does not exist with username: " + username);
        }
    }

    @When("register user with username {string} and password {string}")
    public void register_user_with_username_and_password(String username, String password) {
        User user = User.builder().username(username).password(password).build();
        this.postSaveUser(user);
    }

    private void postSaveUser(User user) {
        String url = urlBase + "/users/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity(url, request, User.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
        resultUser = response.getBody();
        if (resultUser == null) {
            throw new AssertionError("Result user not received in response");
        }
    }

    @When("delete user with username {string}")
    public void delete_user_with_username(String username) {
        String url = urlBase + "/users/delete/" + username;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
    }

    @Then("user is created with username {string} and password {string}")
    public void user_is_created_with_username_and_password(String expectedUsername, String expectedPassword) {
        if (!resultUser.getUsername().equals(expectedUsername)) {
            throw new AssertionError("Result user's username is not expected");
        }
        if (!resultUser.getPassword().equals(expectedPassword)) {
            throw new AssertionError("Result user's password is not expected");
        }
    }

    @And("can find user by username {string}")
    public void can_find_user_by_username(String searchUsername) {
        resultUser = findByUsername(searchUsername);
        if (resultUser == null) {
            throw new AssertionError("Cannot find user by username: " + searchUsername);
        }
        if (!resultUser.getUsername().equals(searchUsername)) {
            throw new AssertionError("Wrong user found with username: " + resultUser.getUsername() + ", expected username: " + searchUsername);
        }
    }

    @And("cannot find user by username {string}")
    public void cannot_find_user_by_username(String absentUsername) {
        resultUser = findByUsername(absentUsername);
        if (resultUser != null) {
            throw new AssertionError("Unexpectedly found user by username: " + absentUsername);
        }
    }

    private User findByUsername(String username) {
        String url = urlBase + "/users/" + username;
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
        return response.getBody();
    }
}