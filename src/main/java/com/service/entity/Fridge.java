package com.service.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

/**
 * The type Fridge represents a Fridge entity that can house a collection of {@link Item} entities.
 */
@Data
@Entity
@RestResource
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fridge {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @OneToMany(mappedBy = "fridge")
    @ToString.Exclude
    List<Item> items;

}
