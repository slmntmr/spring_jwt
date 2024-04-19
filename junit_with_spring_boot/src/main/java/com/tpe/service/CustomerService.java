package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getById(Long id) {

        return customerRepository.findById(id).orElseThrow(()->
                new RuntimeException("User not found"));
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
