package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAndGetCustomer() {
        // Given
        String customerJson = """
        {
            "name": "Test Customer",
            "address": {
                "city": "Test City",
                "street": "Test Street",
                "zipcode": "12345"
            }
        }
        """;

        // When create
        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                "/api/customers",
                customerJson,
                String.class
        );

        // Then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // When get all
        ResponseEntity<String> getAllResponse = restTemplate.getForEntity(
                "/api/customers",
                String.class
        );

        // Then
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAllResponse.getBody()).contains("Test Customer");
    }
}