package com.service.entity.association;

import com.service.BaseFridgeServiceApplication;
import com.service.entity.Fridge;
import com.service.entity.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;


/**
 * The Fridge Service Fridge Item Entity Association Tests ensure that
 * the API operations used for putting an {@link Item} into a {@link Fridge} work.
 */
public class FridgeServiceFridgeItemEntityAssociationTests extends BaseFridgeServiceApplication {

    /**
     * Create Fridge and Item entities before each Test
     */
    @BeforeEach
    void setupAssociationTestEntities() {
        // Create basic Fridge entity
        Fridge fridge = new Fridge();
        fridge.setName(FRIDGE_NAME);
        ResponseEntity<Fridge> fridgeResponseEntity = template.postForEntity(FRIDGE_ENDPOINT, fridge, Fridge.class);

        // Verify that the POST request HTTP status was success
        assertEquals("Fridge entity creation failed.", 201, fridgeResponseEntity.getStatusCodeValue());

        // Determine the path of the newly created Fridge.
        Fridge createdFridge = fridgeResponseEntity.getBody();
        String newFridgeEndpointUrl = fridgeResponseEntity.getHeaders().getLocation().toString();

        // Verify the Fridge exists by querying it
        ResponseEntity<Fridge> existingFridgeResponseEntity = template.getForEntity(newFridgeEndpointUrl, Fridge.class);

        // Assert that the expected name of this fridge is correct
        assertEquals("Fridge Creation Failed", fridge.getName(), existingFridgeResponseEntity.getBody().getName());

        // Create basic Item entity
        Item item = new Item();
        item.setName(ITEM_NAME);
        ResponseEntity<Item> itemResponseEntity = template.postForEntity(ITEM_ENDPOINT, item, Item.class);
        // Verify that the POST request HTTP status was success
        assertEquals("Item entity creation failed.", 201, itemResponseEntity.getStatusCodeValue());

        // Create basic Item entity
        Item itemTwo = new Item();
        itemTwo.setName(ITEM_NAME_TWO);
        ResponseEntity<Item> itemTwoResponseEntity = template.postForEntity(ITEM_ENDPOINT, itemTwo, Item.class);
        // Verify that the POST request HTTP status was success
        assertEquals("Item Two entity creation failed.", 201, itemTwoResponseEntity.getStatusCodeValue());

        // Verify the Items exist by querying them
        ResponseEntity<LinkedHashMap> existingItemsResponseEntity = template.getForEntity(ITEM_ENDPOINT, LinkedHashMap.class);
        List items = (List) ((LinkedHashMap) existingItemsResponseEntity.getBody().get("_embedded")).get("items");

        // Assert that 2 items exist
        assertEquals("Unable to view expected Items.", 2, items.size());
    }

    @Test
    void addItemToFridge() {
        // Define our HTTP Headers for uri-list
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");

        // Create Http Entity that points to our first Fridge entity
        HttpEntity<String> itemHttpEntity = new HttpEntity<>(FRIDGE_ENDPOINT + "/1", requestHeaders);

        // Associate an Item with a Fridge. In other words, for an Item into a Fridge.
        template.exchange(ITEM_ENDPOINT + "/2/fridge", HttpMethod.PUT, itemHttpEntity, String.class);
        template.exchange(ITEM_ENDPOINT + "/3/fridge", HttpMethod.PUT, itemHttpEntity, String.class);

        // View the Fridges contents.
        ResponseEntity<Fridge> fridgeGetResponse = template.getForEntity(ITEM_ENDPOINT + "/1/fridge", Fridge.class);

        // Determine the amount of items in the first Fridge
        int itemsInFirstFridgeCount = ((List) ((LinkedHashMap) ((LinkedHashMap) template.getForEntity(FRIDGE_ENDPOINT + "/1/items", Object.class).getBody()).get("_embedded")).get("items")).size();

        // Verify the first Fridge contains the expected items.
        assertEquals("Expected two items in Fridge.", 2, itemsInFirstFridgeCount);
    }

    @Test
    void removeItemFromFridge() {
        // Define our HTTP Headers for uri-list
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");

        // Create Http Entity that points to our first Fridge entity
        HttpEntity<String> itemHttpEntity = new HttpEntity<>(FRIDGE_ENDPOINT + "/1", requestHeaders);

        // Associate an Item with a Fridge. In other words, for an Item into a Fridge.
        template.exchange(ITEM_ENDPOINT + "/2/fridge", HttpMethod.PUT, itemHttpEntity, String.class);
        template.exchange(ITEM_ENDPOINT + "/3/fridge", HttpMethod.PUT, itemHttpEntity, String.class);

        // View the Fridges contents.
        ResponseEntity<Fridge> fridgeGetResponse = template.getForEntity(ITEM_ENDPOINT + "/1/fridge", Fridge.class);

        // Determine the amount of items in the first Fridge
        int itemsInFirstFridgeCount = ((List) ((LinkedHashMap) ((LinkedHashMap) template.getForEntity(FRIDGE_ENDPOINT + "/1/items", Object.class).getBody()).get("_embedded")).get("items")).size();

        // Verify the first Fridge contains the expected items.
        assertEquals("Expected two items in Fridge.", 2, itemsInFirstFridgeCount);

        // Remove Item 3 from the Fridge.
        template.delete(ITEM_ENDPOINT + "/3/fridge");

        // Determine the amount of items in the first Fridge
        int updatedItemsInFirstFridgeCount = ((List) ((LinkedHashMap) ((LinkedHashMap) template.getForEntity(FRIDGE_ENDPOINT + "/1/items", Object.class).getBody()).get("_embedded")).get("items")).size();

        // Verify the first Fridge contains the expected items.
        assertEquals("Expected one item in Fridge.", 1, updatedItemsInFirstFridgeCount);
    }

}
