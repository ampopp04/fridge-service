package com.service.entity.validator;

import com.service.BaseFridgeServiceApplication;
import com.service.entity.Fridge;
import com.service.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * The type Fridge soda item quantity validator test houses basic tests to ensure
 * the validation constraint logic works as expected.
 */
public class FridgeSodaItemQuantityValidatorTest extends BaseFridgeServiceApplication {

    /**
     * The Max soda cans in fridge.
     */
    @Value("${constraint.quantity.item.maximum}")
    public Integer MAX_SODA_CANS_IN_FRIDGE;

    /**
     * The Soda can item type name.
     */
    @Value("${constraint.quantity.item.name}")
    public String SODA_CAN_ITEM_TYPE_NAME;

    /**
     * Validate item link count constraint.
     * <p>
     * Ex. Only X amount of a specific item named Y are allowed in a
     * specific fridge named Z.
     */
    @Test
    void validateItemLinkCountConstraint() {
        // Create basic Fridge entity
        Fridge fridge = new Fridge();
        fridge.setName(FRIDGE_NAME);
        template.postForEntity(FRIDGE_ENDPOINT, fridge, Fridge.class);

        // Create many Soda items
        for (int i = 0; i < 30; i++) {
            // Create basic Item entity
            Item item = new Item();
            item.setName(SODA_CAN_ITEM_TYPE_NAME);
            template.postForEntity(ITEM_ENDPOINT, item, Item.class);
        }

        // Define our HTTP Headers for uri-list
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");

        // Create Http Entity that points to our first Fridge entity
        HttpEntity<String> itemHttpEntity = new HttpEntity<>(FRIDGE_ENDPOINT + "/1", requestHeaders);

        // Put 20 Soda Items into the Fridge.
        for (int i = 2; i < 22; i++) {
            // Associate an Item with a Fridge. In other words, for an Item into a Fridge.
            template.exchange(ITEM_ENDPOINT + "/" + i + "/fridge", HttpMethod.PUT, itemHttpEntity, String.class);
        }

        // Determine the amount of items in the first Fridge
        int itemsInFirstFridgeCount = ((List) ((LinkedHashMap) ((LinkedHashMap) template.getForEntity(FRIDGE_ENDPOINT + "/1/items", Object.class).getBody()).get("_embedded")).get("items")).size();

        // Verify the first Fridge contains the expected items.
        assertEquals("Expected [" + MAX_SODA_CANS_IN_FRIDGE + "] items in Fridge.", MAX_SODA_CANS_IN_FRIDGE, itemsInFirstFridgeCount);
    }

}