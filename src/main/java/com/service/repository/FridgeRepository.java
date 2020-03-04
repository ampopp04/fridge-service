package com.service.repository;

import com.service.entity.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Fridge repository.
 */
public interface FridgeRepository extends JpaRepository<Fridge, Long> {
}