package com.codechallenge.service;

import com.codechallenge.entity.Customer;
import com.codechallenge.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl {

    @Autowired
    CustomerRepository customerRepository;


    public Customer getCustomerById(String id){
       Optional<Customer> customer= customerRepository.findById(id);
       if(customer.isPresent())
           return customer.get();
       return new Customer();
    }


    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }



    public String createCustomer(Customer customer) {
        Customer customerData= customerRepository.save(customer);
        return "Data Saved";
    }


    public String deleteCustomer(String id) {
        customerRepository.deleteById(id);
        return "User got Deleted";
    }

    public String updateCustomer(Customer customer) {
       Optional<Customer> response= customerRepository.findById(customer.getId());
       if(response.isPresent()){
         Customer newCustomer=response.get();
         newCustomer.setEmail(customer.getEmail());
         newCustomer.setId(customer.getId());
         newCustomer.setPhone(customer.getPhone());
         customerRepository.save(newCustomer);
       }
       else{
           customerRepository.save(customer);
       }
        return "DataUpdated";
    }
}
