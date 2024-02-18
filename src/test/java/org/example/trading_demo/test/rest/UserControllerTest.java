package org.example.trading_demo.test.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.trading_demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

//    @Value("${api.url}")
//    private String urlBase;

    @Test
    public void test() {
        String url = "http://localhost:" + port + "/api/v1/users/save";
        log.info("------- ========== testing url: {} ", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = User.builder().username("restuser1").password("restpassword1").build();
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<User> response = restTemplate.postForEntity(url, request, User.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new AssertionError("Unexpected status code: " + response.getStatusCode());
        }
        User resultUser = response.getBody();
        if (resultUser == null) {
            throw new AssertionError("Result user not received in response");
        }
        assertEquals(resultUser.getUsername(), "restuser1");
        assertEquals(resultUser.getPassword(), "restpassword1");
    }
}


