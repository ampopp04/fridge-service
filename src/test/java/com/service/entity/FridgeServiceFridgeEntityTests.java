package com.service.entity;

import com.service.BaseFridgeServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

/**
 * The Fridge Service Fridge Entity Tests ensures that the
 * basic {@link Fridge} API operations work as expected.
 */
public class FridgeServiceFridgeEntityTests extends BaseFridgeServiceApplication {

    @Test
    void createFridge() {
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
    }

    @Test
    void deleteFridge() {
        // Create basic Fridge entity
        Fridge fridge = new Fridge();
        fridge.setName(FRIDGE_NAME + "twoosse");
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

        // Delete this fridge
        template.delete(newFridgeEndpointUrl);

        // Verify the Fridge doesn't exist by querying it
        ResponseEntity<Fridge> deletedFridgeResponseEntity = template.getForEntity(newFridgeEndpointUrl, Fridge.class);

        // Assert that the fridge wasn't found.
        assertEquals("Fridge Exists but was deleted.", 404, deletedFridgeResponseEntity.getStatusCodeValue());
    }

    @Test
    void updateFridge() {
        // Create basic Fridge entity
        Fridge fridge = new Fridge();
        fridge.setName(FRIDGE_NAME);
        ResponseEntity<Fridge> fridgeResponseEntity = template.postForEntity(FRIDGE_ENDPOINT, fridge, Fridge.class);

        // Verify that the POST request HTTP status was success
        assertEquals("Fridge entity creation failed.", 201, fridgeResponseEntity.getStatusCodeValue());

        // Determine the path of the newly created Fridge.
        String newFridgeEndpointUrl = fridgeResponseEntity.getHeaders().getLocation().toString();

        // Verify the Fridge exists by querying it
        ResponseEntity<Fridge> existingFridgeResponseEntity = template.getForEntity(newFridgeEndpointUrl, Fridge.class);

        // Assert that the expected name of this fridge is correct
        assertEquals("Fridge Creation Failed", fridge.getName(), existingFridgeResponseEntity.getBody().getName());

        // Update the name of this Fridge
        Fridge createdFridge = existingFridgeResponseEntity.getBody();
        createdFridge.setName(FRIDGE_NAME_TWO);

        // Invoke the API endpoint to update this Fridge
        template.put(newFridgeEndpointUrl, fridge, Fridge.class);

        // Verify the Fridge update by querying it
        ResponseEntity<Fridge> updatedFridgeResponseEntity = template.getForEntity(newFridgeEndpointUrl, Fridge.class);

        // Assert that the expected name of this fridge is correct
        assertEquals("Fridge Update Failed", FRIDGE_NAME_TWO, existingFridgeResponseEntity.getBody().getName());
    }

    @Test
    void viewFridges() {
        // Create basic Fridge entity
        Fridge fridge = new Fridge();
        fridge.setName(FRIDGE_NAME);
        ResponseEntity<Fridge> fridgeResponseEntity = template.postForEntity(FRIDGE_ENDPOINT, fridge, Fridge.class);
        // Verify that the POST request HTTP status was success
        assertEquals("Fridge entity creation failed.", 201, fridgeResponseEntity.getStatusCodeValue());

        // Create basic Fridge entity
        Fridge fridgeTwo = new Fridge();
        fridgeTwo.setName(FRIDGE_NAME_TWO);
        ResponseEntity<Fridge> fridgeTwoResponseEntity = template.postForEntity(FRIDGE_ENDPOINT, fridgeTwo, Fridge.class);
        // Verify that the POST request HTTP status was success
        assertEquals("Fridge Two entity creation failed.", 201, fridgeTwoResponseEntity.getStatusCodeValue());

        // Verify the Fridges exist by querying them
        ResponseEntity<LinkedHashMap> existingFridgesResponseEntity = template.getForEntity(FRIDGE_ENDPOINT, LinkedHashMap.class);
        List fridges = (List) ((LinkedHashMap) existingFridgesResponseEntity.getBody().get("_embedded")).get("fridges");

        // Assert that 2 fridges exist
        assertEquals("Unable to view expected Fridges.", 2, fridges.size());
    }

}
