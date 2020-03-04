package com.service.entity.validator;

import com.service.entity.Fridge;
import com.service.entity.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.core.annotation.HandleBeforeLinkSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

/**
 * The type Fridge soda item quantity validator implements a constraint that is evaluated
 * prior to the association of an {@link Item} to a particular {@link Fridge}.
 * <p>
 * This validator implementation constrains a {@link Fridge} to a maximum quantity of a specific {@link Item}
 * <p>
 * Ex. A fridge can only hold a maximum quantity of 12 Soda items.
 */
@Slf4j
@Component
@RepositoryEventHandler
public class FridgeSodaItemQuantityValidator {

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
     *
     * @param item   the item being put into a fridge
     * @param fridge the fridge that will contain the item and may already contain many items.
     */
    @HandleBeforeLinkSave
    public void validateItemLinkCountConstraint(Item item, Fridge fridge) {
        log.info("Validating item link [" + item.toString() + "]");

        long sodaItemCount = item.getFridge().getItems().stream()
                .filter(item1 -> SODA_CAN_ITEM_TYPE_NAME.equals(item1.getName()))
                .count();

        if (sodaItemCount >= MAX_SODA_CANS_IN_FRIDGE) {
            String errorMsg = "Validating item [" + item.toString() + "] exceeded maximum quantity of [" + MAX_SODA_CANS_IN_FRIDGE + "] allowed in a fridge.";
            log.info(errorMsg);

            throw new DataIntegrityViolationException(errorMsg);
        }
    }

}