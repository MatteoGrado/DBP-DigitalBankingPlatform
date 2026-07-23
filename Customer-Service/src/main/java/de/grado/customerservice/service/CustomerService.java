package de.grado.customerservice.service;

import de.grado.customerservice.dto.CreateCustomer;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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

    public Customer getCustomer(BigInteger id)
    {
        return customerRepository.findById(id);
    }

    public String createCustomer(CreateCustomer createCustomer)
    {
        Customer customer = new Customer();

        customer.setAccounts(createCustomer.getAccounts());
        customer.setFirstName(createCustomer.getFirstName());
        customer.setLastName(createCustomer.getLastName());
        customer.setDateOfBirth(createCustomer.getDateOfBirth());
        customer.setAddress(createCustomer.getAddress());
        customer.setHouseNumber(createCustomer.getHouseNumber());
        customer.setZipCode(createCustomer.getZipCode());
        customer.setCity(createCustomer.getCity());
        customer.setState(createCustomer.getState());
        customer.setEmail(createCustomer.getEmail());
        customer.setPhoneNumber(createCustomer.getPhoneNumber());
        customer.setIBAN(createCustomer.getIBAN());

        customerRepository.save(customer);
        return "Customer Created";
    }
}
