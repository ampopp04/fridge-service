package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

/**
 * The Base Fridge Service Application Tests defines an abstraction base test class which encapsulates
 * the reusable logic when setting up basic Fridge Service functional tests.
 */
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = FridgeServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseFridgeServiceApplication {

    /**
     * The constant ITEM_ENDPOINT that defines the API endpoint for the Items resource.
     */
    protected static String ITEM_ENDPOINT = "http://localhost:8080/items/";
    /**
     * The constant FRIDGE_ENDPOINT that defines the API endpoint for the Fridges resource.
     */
    protected static String FRIDGE_ENDPOINT = "http://localhost:8080/fridges/";
    /**
     * The constant FRIDGE_NAME defines a simple reusable Fridge name.
     */
    protected static String FRIDGE_NAME = "My Fridge";
    /**
     * The constant FRIDGE_NAME_TWO defines a simple reusable secondary Fridge name.
     */
    protected static String FRIDGE_NAME_TWO = "My Fridge Two";
    /**
     * The constant ITEM_NAME defines a simple Item name.
     */
    protected static String ITEM_NAME = "Soda";
    /**
     * The constant ITEM_NAME_ONE defines a simple reusable secondary Item name.
     */
    protected static String ITEM_NAME_ONE = "Soda";
    /**
     * The constant ITEM_NAME_TWO defines a simple reusable third Item name.
     */
    protected static String ITEM_NAME_TWO = "Eggs";
    /**
     * The Template used for querying the remote API endpoints.
     */
    @Autowired
    protected TestRestTemplate template;

}
