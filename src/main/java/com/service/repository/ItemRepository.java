package com.service.repository;

import com.service.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Item repository.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
}