package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks // yukarda @Mock ile annote edilen siniflari , CustomrController sinifina oto-mock yapilmasi saglaniyor
    private CustomerController customerController;

    @Test
    void getById() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerService.getById(1L)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getById(1L);
        assertEquals(200 , response.getStatusCodeValue());
        verify(customerService).getById(1L);
    }

    @Test
    void deleteById() {
        customerController.deleteById(1L);
        verify(customerService).deleteById(1L);
    }
}