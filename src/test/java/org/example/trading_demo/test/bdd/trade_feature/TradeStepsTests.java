package org.example.trading_demo.test.bdd.trade_feature;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.example.trading_demo.model.*;
import org.example.trading_demo.repository.SecurityRepository;
import org.example.trading_demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class TradeStepsTests {

    private final RestTemplate restTemplate = new RestTemplate();
    private Trade trade;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityRepository securityRepository;
    @Value("${api.url}")
    private String urlBase;

    @Given("one security {string} and two users {string} and {string} exist")
    public void one_security_and_two_users_exist(String securityName, String username1, String username2) {
        this.createUserIfNotExists(username1);
        this.createUserIfNotExists(username2);
        this.createSecurityIfNotExists(securityName);
    }

    @Given("two securities {string} and {string} and two users {string} and {string} exist")
    public void one_security_and_two_users_exist(String securityName1, String securityName2, String username1, String username2) {
        this.createUserIfNotExists(username1);
        this.createUserIfNotExists(username2);
        this.createSecurityIfNotExists(securityName1);
        this.createSecurityIfNotExists(securityName2);
    }

    private void createUserIfNotExists(String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            user = User.builder().username(username).password("").build();
            this.postSaveUser(user);
        }
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
    }

    private void createSecurityIfNotExists(String securityName) {
        Security security = securityRepository.findByName(securityName);
        if (security == null) {
            security = new Security();
            security.setName(securityName);
            this.postSaveSecurity(security);
        }
    }

    private void postSaveSecurity(Security security) {
        String url = urlBase + "/security/save";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Security> request = new HttpEntity<>(security, headers);
        ResponseEntity<Security> response = restTemplate.postForEntity(url, request, Security.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
    }

    @When("user {string} puts a buy order for security {string} with a price of {int} and quantity of {int}")
    public void user_puts_a_buy_order(String user, String security, int price, int quantity) {
        CustomerOrder buyerOrder = new CustomerOrder(security, user, price, quantity);
        String url = urlBase + "/order/buy";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerOrder> request = new HttpEntity<>(buyerOrder, headers);
        ResponseEntity<CustomerOrder> response = restTemplate.postForEntity(url, request, CustomerOrder.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
    }

    @And("user {string} puts a sell order for security {string} with a price of {int} and a quantity of {int}")
    public void user_puts_a_sell_order(String user, String security, int price, int quantity) {
        CustomerOrder sellerOrder = new CustomerOrder(security, user, price, quantity);
        String url = urlBase + "/order/sell_and_trade";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerOrder> request = new HttpEntity<>(sellerOrder, headers);
        ResponseEntity<Trade> response = restTemplate.postForEntity(url, request, Trade.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
        this.trade = response.getBody();
    }

    @Then("a trade occurs with the price of {int} and quantity of {int}")
    public void trade_occurs(int expectedPrice, int expectedQuantity) {
        if (this.trade.getPrice() != expectedPrice) {
            throw new AssertionError("Trade price is not expected");
        }
        if (this.trade.getQuantity() != expectedQuantity) {
            throw new AssertionError("Trade quantity is not expected");
        }
    }

    @Then("a trade does not occur")
    public void trade_does_not_occur() {
        if (this.trade != null) {
            throw new AssertionError("Trade result is not expected");
        }
        log.info("Trade does not occur, Trade: " + this.trade);
    }

}