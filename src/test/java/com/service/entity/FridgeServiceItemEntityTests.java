package com.service.entity;

import com.service.BaseFridgeServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * The Fridge Service Item Entity Tests ensures that the
 * basic {@link Item} API operations work as expected.
 */
public class FridgeServiceItemEntityTests extends BaseFridgeServiceApplication {

    @Test
    void createItem() {
        // Create basic Item entity
        Item item = new Item();
        item.setName(ITEM_NAME);
        ResponseEntity<Item> itemResponseEntity = template.postForEntity(ITEM_ENDPOINT, item, Item.class);

        // Verify that the POST request HTTP status was success
        assertEquals("Item entity creation failed.", 201, itemResponseEntity.getStatusCodeValue());

        // Determine the path of the newly created Item.
        Item createdItem = itemResponseEntity.getBody();
        String newItemEndpointUrl = itemResponseEntity.getHeaders().getLocation().toString();

        // Verify the Item exists by querying it
        ResponseEntity<Item> existingItemResponseEntity = template.getForEntity(newItemEndpointUrl, Item.class);

        // Assert that the expected name of this item is correct
        assertEquals("Item Creation Failed", item.getName(), existingItemResponseEntity.getBody().getName());
    }

    @Test
    void deleteItem() {
        // Create basic Item entity
        Item item = new Item();
        item.setName(ITEM_NAME + "twoosse");
        ResponseEntity<Item> itemResponseEntity = template.postForEntity(ITEM_ENDPOINT, item, Item.class);

        // Verify that the POST request HTTP status was success
        assertEquals("Item entity creation failed.", 201, itemResponseEntity.getStatusCodeValue());

        // Determine the path of the newly created Item.
        Item createdItem = itemResponseEntity.getBody();
        String newItemEndpointUrl = itemResponseEntity.getHeaders().getLocation().toString();

        // Verify the Item exists by querying it
        ResponseEntity<Item> existingItemResponseEntity = template.getForEntity(newItemEndpointUrl, Item.class);

        // Assert that the expected name of this item is correct
        assertEquals("Item Creation Failed", item.getName(), existingItemResponseEntity.getBody().getName());

        // Delete this item
        template.delete(newItemEndpointUrl);

        // Verify the Item doesn't exist by querying it
        ResponseEntity<Item> deletedItemResponseEntity = template.getForEntity(newItemEndpointUrl, Item.class);

        // Assert that the item wasn't found.
        assertEquals("Item Exists but was deleted.", 404, deletedItemResponseEntity.getStatusCodeValue());
    }

    @Test
    void updateItem() {
        // Create basic Item entity
        Item item = new Item();
        item.setName(ITEM_NAME);
        ResponseEntity<Item> itemResponseEntity = template.postForEntity(ITEM_ENDPOINT, item, Item.class);

        // Verify that the POST request HTTP status was success
        assertEquals("Item entity creation failed.", 201, itemResponseEntity.getStatusCodeValue());

        // Determine the path of the newly created Item.
        String newItemEndpointUrl = itemResponseEntity.getHeaders().getLocation().toString();

        // Verify the Item exists by querying it
        ResponseEntity<Item> existingItemResponseEntity = template.getForEntity(newItemEndpointUrl, Item.class);

        // Assert that the expected name of this item is correct
        assertEquals("Item Creation Failed", item.getName(), existingItemResponseEntity.getBody().getName());

        // Update the name of this Item
        Item createdItem = existingItemResponseEntity.getBody();
        createdItem.setName(ITEM_NAME_TWO);

        // Invoke the API endpoint to update this Item
        template.put(newItemEndpointUrl, item, Item.class);

        // Verify the Item update by querying it
        ResponseEntity<Item> updatedItemResponseEntity = template.getForEntity(newItemEndpointUrl, Item.class);

        // Assert that the expected name of this item is correct
        assertEquals("Item Update Failed", ITEM_NAME_TWO, existingItemResponseEntity.getBody().getName());
    }

    @Test
    void viewItems() {
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

}
