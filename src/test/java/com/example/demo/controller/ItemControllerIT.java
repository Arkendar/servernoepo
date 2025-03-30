package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class ItemControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndGetItem() {
        // Given
        String itemJson = """
        {
            "description": "Test Item",
            "shippingWeight": {
                "value": 1.5
            }
        }
        """;

        // When create
        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                "/api/items",
                itemJson,
                String.class
        );

        // Then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // When get all
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(
                "/api/items",
                String.class
        );

        // Then
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAllResponse.getBody()).contains("Test Item");
    }
}