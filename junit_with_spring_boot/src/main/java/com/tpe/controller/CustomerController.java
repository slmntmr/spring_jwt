package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Not: Unit Test icin kisayol : Ctrl + Shift + T
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id){
        Customer customer = customerService.getById(id);
        return  ResponseEntity.ok(customer); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        customerService.deleteById(id);
        String responseMessage = "User is deleted Successfully";
        return ResponseEntity.ok(responseMessage);
    }
}
