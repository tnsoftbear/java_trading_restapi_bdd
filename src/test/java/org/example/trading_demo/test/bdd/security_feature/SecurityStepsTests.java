package org.example.trading_demo.test.bdd.security_feature;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.example.trading_demo.model.Security;
import org.example.trading_demo.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SecurityStepsTests {

    private final RestTemplate restTemplate = new RestTemplate();
    private Security resultSecurity;
    @Autowired
    private SecurityRepository securityRepo;
    @Value("${api.url}")
    private String urlBase;

    @Given("security with name {string} does not exist")
    public void security_with_name_does_not_exist(String name) {
        Security security = securityRepo.findByName(name);
        if (security != null) {
            throw new AssertionError("Security already exists with name: " + name);
        }
    }

    @Given("security with name {string} exists")
    public void security_with_name_exists(String name) {
        Security security = securityRepo.findByName(name);
        if (security == null) {
            throw new AssertionError("Security does not exist with name: " + name);
        }
    }

    @When("register security with name {string}")
    public void register_security_with_name(String name) {
        Security security = new Security();
        security.setName(name);
        this.postSaveSecurity(security);
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
        resultSecurity = response.getBody();
        if (resultSecurity == null) {
            throw new AssertionError("Result security not received in response");
        }
    }

    @When("delete security with name {string}")
    public void delete_security_with_name(String name) {
        String url = urlBase + "/security/delete/" + name;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
    }

    @Then("security is created with name {string}")
    public void security_is_created_with_name(String expectedName) {
        if (!resultSecurity.getName().equals(expectedName)) {
            throw new AssertionError("Result security's name is not expected");
        }
    }

    @And("can find security by name {string}")
    public void can_find_security_by_name(String searchName) {
        resultSecurity = findByName(searchName);
        if (resultSecurity == null) {
            throw new AssertionError("Cannot find security by name: " + searchName);
        }
        if (!resultSecurity.getName().equals(searchName)) {
            throw new AssertionError("Wrong security found with name: " + resultSecurity.getName() + ", expected name: " + searchName);
        }
    }

    @And("cannot find security by name {string}")
    public void cannot_find_security_by_name(String absentName) {
        resultSecurity = findByName(absentName);
        if (resultSecurity != null) {
            throw new AssertionError("Unexpectedly found security by name: " + absentName);
        }
    }

    private Security findByName(String name) {
        String url = urlBase + "/security/" + name;
        ResponseEntity<Security> response = restTemplate.getForEntity(url, Security.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
        return response.getBody();
    }
}