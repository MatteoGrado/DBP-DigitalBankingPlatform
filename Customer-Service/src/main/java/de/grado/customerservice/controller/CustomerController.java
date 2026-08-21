package de.grado.customerservice.controller;

import de.grado.customerservice.dto.CreateCustomerDto;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController
{
    private final CustomerService customerService;

    @PostMapping("/createCustomer")
    public void createCustomer(@RequestBody CreateCustomerDto createCustomerDto)
    {
        customerService.createCustomer(createCustomerDto);
        log.info("Created Customer");
    }

    @GetMapping("/getCustomer")
    public List<Customer> getCustomerList()
    {
        log.info("Get Customers");
        return customerService.getCustomers();
    }

    @GetMapping("/getCustomer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id)
    {
        log.info("Get Customer by ID");
        return customerService.getCustomer(id);
    }
}
