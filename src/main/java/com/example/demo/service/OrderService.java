package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    // Базовые CRUD-методы
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    // Метод для поиска по критериям
    public List<Order> searchOrders(OrderSearchCriteria criteria) {
        return orderRepository.findAll(buildSpecification(criteria));
    }

    private Specification<Order> buildSpecification(OrderSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Фильтр по адресу доставки
            if (criteria.getAddress() != null) {
                Join<Order, Customer> customerJoin = root.join("customer");
                predicates.add(cb.equal(customerJoin.get("address").get("city"), criteria.getAddress().getCity()));
                predicates.add(cb.equal(customerJoin.get("address").get("street"), criteria.getAddress().getStreet()));
                predicates.add(cb.equal(customerJoin.get("address").get("zipcode"), criteria.getAddress().getZipcode()));
            }

            // Фильтр по временному интервалу
            if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                predicates.add(cb.between(root.get("date"), criteria.getStartDate(), criteria.getEndDate()));
            }

            // Фильтр по способу оплаты
            if (criteria.getPaymentType() != null) {
                Join<Order, Payment> paymentJoin = root.join("payment");
                predicates.add(cb.equal(paymentJoin.type(), criteria.getPaymentType()));
            }

            // Фильтр по имени пользователя
            if (criteria.getCustomerName() != null) {
                Join<Order, Customer> customerJoin = root.join("customer");
                predicates.add(cb.equal(customerJoin.get("name"), criteria.getCustomerName()));
            }

            // Фильтр по статусу заказа
            if (criteria.getOrderStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getOrderStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}