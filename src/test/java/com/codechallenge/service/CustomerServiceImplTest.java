package com.codechallenge.service;

import com.codechallenge.entity.Customer;
import com.codechallenge.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository repository;

    @Test
    public void addUserInfoTest(){

        Mockito.when(repository.save(Mockito.any())).thenReturn(Customer.builder().build());
        assertEquals("Data Saved",customerService.createCustomer(Customer.builder().name("test").build()));

    }
}
