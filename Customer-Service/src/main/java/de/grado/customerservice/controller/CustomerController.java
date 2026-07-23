package de.grado.customerservice.controller;

import de.grado.customerservice.model.Customer;
import de.grado.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController
{
    private final CustomerService customerService;

    @GetMapping("/getCustomers")
    public List<Customer> getCustomers()
    {
        log.info("Listed all existing Customers");
        return customerService.getCustomers();
    }

    @GetMapping("/getCustomer/{firstName}/{lastName}")
    public List<Customer> getCustomer(@PathVariable String firstName, @PathVariable String lastName)
    {
        log.info("Listed searched Customers");
        return customerService.getCustomerList(firstName, lastName);
    }
}
