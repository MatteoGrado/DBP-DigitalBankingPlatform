package de.grado.customerservice.controller;

import de.grado.customerservice.dto.CreateCustomer;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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
    public List<Customer> getCustomerList(@PathVariable String firstName, @PathVariable String lastName)
    {
        log.info("Listed searched Customers");
        return customerService.getCustomerList(firstName, lastName);
    }

    @GetMapping("/getCustomer/{id}")
    public Customer getCustomer(@PathVariable BigInteger id)
    {
        log.info("Found Customer {}", id);
        return customerService.getCustomer(id);
    }

    @PostMapping("/createCustomer")
    public String createCustomer(@RequestBody CreateCustomer createCustomer)
    {
        log.info("Created Customer");
        return customerService.createCustomer(createCustomer);
    }
}
