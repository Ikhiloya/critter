package com.udacity.jdnd.course3.critter.user.service;

import com.udacity.jdnd.course3.critter.user.domain.Customer;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerService.class.getSimpleName());

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }


}
