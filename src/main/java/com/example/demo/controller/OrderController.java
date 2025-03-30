package com.example.demo.controller;
import com.example.demo.entity.*;
import com.example.demo.other.CreateOrderRequest;
import com.example.demo.service.CustomerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.OrderSearchCriteria;
import com.example.demo.value.OrderDetail;
import com.example.demo.value.Quantity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping("/search")
    public ResponseEntity<List<Order>> searchOrders(@RequestBody OrderSearchCriteria criteria) {
        return ResponseEntity.ok(orderService.searchOrders(criteria));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Customer customer = customerService.getById(request.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));

        Order order = new Order();
        order.setDate(request.getDate());
        order.setStatus(request.getStatus());
        order.setCustomer(customer); // Устанавливаем существующего Customer

        // Создаем Payment
        Payment payment = createPayment(request.getPayment());
        order.setPayment(payment);

        // Создаем OrderDetails
        List<OrderDetail> details = request.getDetails().stream()
                .map(dto -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setQuantity(new Quantity(dto.getQuantity())); // Создаем объект Quantity
                    detail.setTaxStatus(dto.getTaxStatus());
                    return detail;
                })
                .collect(Collectors.toList());
        order.setDetails(details);

        return ResponseEntity.ok(orderService.create(order));
    }

    private Payment createPayment(CreateOrderRequest.PaymentDto paymentDto) {
        if (paymentDto instanceof CreateOrderRequest.CashDto) {
            Cash cash = new Cash();
            cash.setAmount(paymentDto.getAmount());
            cash.setCashTendered(((CreateOrderRequest.CashDto) paymentDto).getCashTendered());
            return cash;
        } else if (paymentDto instanceof CreateOrderRequest.CheckDto) {
            Check check = new Check();
            check.setAmount(paymentDto.getAmount());
            check.setName(((CreateOrderRequest.CheckDto) paymentDto).getName());
            check.setBankID(((CreateOrderRequest.CheckDto) paymentDto).getBankID());
            return check;
        } else if (paymentDto instanceof CreateOrderRequest.CreditDto) {
            Credit credit = new Credit();
            credit.setAmount(paymentDto.getAmount());
            credit.setNumber(((CreateOrderRequest.CreditDto) paymentDto).getNumber());
            credit.setCardType(((CreateOrderRequest.CreditDto) paymentDto).getCardType());
            credit.setExpDate(((CreateOrderRequest.CreditDto) paymentDto).getExpDate());
            return credit;
        }
        throw new IllegalArgumentException("Unknown payment type");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        return ResponseEntity.ok(orderService.update(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}