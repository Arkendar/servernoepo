package com.example.demo.service;

import com.example.demo.AbstractIntegrationTest;
import com.example.demo.entity.*;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.value.OrderDetail;
import com.example.demo.value.Quantity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceIT extends AbstractIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldCreateAndFindOrder() {
        // Given
        Customer customer = customerRepository.save(new Customer());

        Order order = new Order();
        order.setDate(LocalDateTime.now());
        order.setStatus("NEW");
        order.setCustomer(customer);

        OrderDetail detail = new OrderDetail();
        detail.setQuantity(new Quantity(2));
        detail.setTaxStatus("TAXABLE");
        order.setDetails(List.of(detail));

        Cash payment = new Cash();
        payment.setAmount(100.0f);
        payment.setCashTendered(100.0f);
        order.setPayment(payment);

        // When
        Order savedOrder = orderService.create(order);
        List<Order> foundOrders = orderService.getAll();

        // Then
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(foundOrders).hasSize(1);
        assertThat(foundOrders.get(0).getDetails().get(0).getQuantity().getValue()).isEqualTo(2);
    }
}