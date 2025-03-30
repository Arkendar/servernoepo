package com.example.demo.service;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerServiceIT extends AbstractIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void shouldCreateAndRetrieveCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("John Doe");

        Address address = new Address();
        address.setCity("New York");
        address.setStreet("5th Avenue");
        address.setZipcode("10001");
        customer.setAddress(address);

        // When
        Customer savedCustomer = customerService.create(customer);
        Optional<Customer> foundCustomer = customerService.getById(savedCustomer.getId());

        // Then
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get().getName()).isEqualTo("John Doe");
        assertThat(foundCustomer.get().getAddress().getCity()).isEqualTo("New York");
    }

    @Test
    void shouldGetAllCustomers() {
        // Given
        Customer customer1 = new Customer();
        customer1.setName("Customer 1");
        customerService.create(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Customer 2");
        customerService.create(customer2);

        // When
        var customers = customerService.getAll();

        // Then
        assertThat(customers).hasSizeGreaterThanOrEqualTo(2);
    }
}