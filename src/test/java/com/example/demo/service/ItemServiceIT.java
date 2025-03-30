package com.example.demo.service;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.Item;
import com.example.demo.value.Weight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ItemServiceIT extends AbstractIntegrationTest {

    @Autowired
    private ItemService itemService;

    @Test
    void shouldCreateAndRetrieveItem() {
        // Given
        Item item = new Item();
        item.setDescription("Test Item");

        Weight weight = new Weight();
        weight.setValue(new BigDecimal("1.50"));
        item.setShippingWeight(weight);

        // When
        Item savedItem = itemService.create(item);
        Optional<Item> foundItem = itemService.getById(savedItem.getId());

        // Then
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getDescription()).isEqualTo("Test Item");
        assertThat(foundItem.get().getShippingWeight().getValue()).isEqualByComparingTo("1.50");
    }

    @Test
    void shouldUpdateItem() {
        // Given
        Item item = new Item();
        item.setDescription("Initial Description");
        Item savedItem = itemService.create(item);

        // When
        savedItem.setDescription("Updated Description");
        Item updatedItem = itemService.update(savedItem);

        // Then
        assertThat(updatedItem.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    void shouldDeleteItem() {
        // Given
        Item item = new Item();
        item.setDescription("To be deleted");
        Item savedItem = itemService.create(item);

        // When
        itemService.delete(savedItem.getId());
        Optional<Item> foundItem = itemService.getById(savedItem.getId());

        // Then
        assertThat(foundItem).isEmpty();
    }

    @Test
    void shouldGetAllItems() {
        // Given
        Item item1 = new Item();
        item1.setDescription("Item 1");
        itemService.create(item1);

        Item item2 = new Item();
        item2.setDescription("Item 2");
        itemService.create(item2);

        // When
        var items = itemService.getAll();

        // Then
        assertThat(items).hasSizeGreaterThanOrEqualTo(2);
    }
}