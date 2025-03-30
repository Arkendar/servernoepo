package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public Optional<Customer> getById(Long id) {
        return repository.findById(id);
    }
}