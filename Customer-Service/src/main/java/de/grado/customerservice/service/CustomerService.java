package de.grado.customerservice.service;

import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerService
{
    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomerList(String firstName, String lastName)
    {
        return customerRepository.findByName(firstName, lastName);
    }
}
