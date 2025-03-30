package com.example.demo.controller;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OrderControllerIT extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    private Long customerId;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer = customerRepository.save(customer);
        customerId = customer.getId();
    }

    @Test
    void shouldCreateOrder() {
        // Given
        String orderJson = """
        {
            "date": "%s",
            "status": "NEW",
            "customerId": %d,
            "details": [
                {
                    "quantity": 2,
                    "taxStatus": "TAXABLE"
                }
            ],
            "payment": {
                "type": "CASH",
                "amount": 100.0,
                "cashTendered": 100.0
            }
        }
        """.formatted(LocalDateTime.now(), customerId);

        // When
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/orders",
                orderJson,
                String.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"status\":\"NEW\"");
    }

    @Test
    void shouldGetAllOrders() {
        // When
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/orders",
                String.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldUpdateOrder() {
        // First create an order
        String createJson = """
        {
            "date": "%s",
            "status": "NEW",
            "customerId": %d,
            "details": [
                {
                    "quantity": 2,
                    "taxStatus": "TAXABLE"
                }
            ],
            "payment": {
                "type": "CASH",
                "amount": 100.0,
                "cashTendered": 100.0
            }
        }
        """.formatted(LocalDateTime.now(), customerId);

        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                "/api/orders",
                createJson,
                String.class
        );

        // Then update it
        String orderId = extractIdFromResponse(createResponse.getBody());
        String updateJson = """
        {
            "id": %s,
            "date": "%s",
            "status": "PROCESSING",
            "customerId": %d,
            "details": [
                {
                    "quantity": 3,
                    "taxStatus": "TAXABLE"
                }
            ],
            "payment": {
                "type": "CASH",
                "amount": 150.0,
                "cashTendered": 150.0
            }
        }
        """.formatted(orderId, LocalDateTime.now(), customerId);

        ResponseEntity<String> updateResponse = restTemplate.exchange(
                "/api/orders/" + orderId,
                HttpMethod.PUT,
                new HttpEntity<>(updateJson),
                String.class
        );

        // Verify
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).contains("\"status\":\"PROCESSING\"");
    }

    private String extractIdFromResponse(String responseBody) {
        // Simple extraction - in real project use proper JSON parsing
        return responseBody.split("\"id\":")[1].split(",")[0].trim();
    }
}