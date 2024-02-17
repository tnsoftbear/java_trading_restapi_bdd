package org.example.trading_demo.test.common;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepHooks {

    @BeforeAll
    public static void beforeAll() {
        // log.info("beforeAll: --------------------------------------------------------------");
    }

    @AfterAll
    public static void afterAll() {
        // log.info("afterAll: --------------------------------------------------------------");
    }

    @Before
    public void setUp() {
        // log.info("Up: --------------------------------------------------------------");
    }

    @After
    public void tearDown() {
        // log.info("Down: --------------------------------------------------------------");
    }
}
