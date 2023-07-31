package com.codechallenge.controller;

import com.codechallenge.entity.Customer;
import com.codechallenge.service.CustomerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class CustomerController {

    @Autowired
    CustomerServiceImpl customerServiceImpl;



    @Operation(summary = "Api endpoint to add customers")
    @PostMapping(value = "/customers")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        String response = customerServiceImpl.createCustomer(customer);
        return new ResponseEntity < >(response, HttpStatus.CREATED);
    }


    @Operation(summary = "Api endpoint to delete customer with given id")
    @DeleteMapping(value = "/customers/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity < String > deleteCustomer(@PathVariable String id) {
        String response= customerServiceImpl.deleteCustomer(id);
        return new ResponseEntity < >(response,HttpStatus.OK);
    }

    @Operation(summary = "Api endpoint to get customer details with given id")
    @GetMapping(value = "/customers/{id}")
    public ResponseEntity <Customer> getCustomerById(@PathVariable String id) {
        Customer customer = customerServiceImpl.getCustomerById(id);
        return new ResponseEntity < >(customer, HttpStatus.OK);
    }

    @Operation(summary = "Api endpoint to get all customer data")
    @GetMapping(value = "/customers")
    public ResponseEntity <List<Customer>> getAllCustomers() {
        List<Customer> customer = customerServiceImpl.getCustomers();
        return new ResponseEntity < >(customer, HttpStatus.OK);
    }

    @Operation(summary = "Api endpoint to update existing customer")
    @PutMapping(value = "/updateCustomer")
    public ResponseEntity < String > updateCustomer(@RequestBody Customer customer) {
        String response = customerServiceImpl.updateCustomer(customer);
        return new ResponseEntity < >(response, HttpStatus.OK);
    }


}
