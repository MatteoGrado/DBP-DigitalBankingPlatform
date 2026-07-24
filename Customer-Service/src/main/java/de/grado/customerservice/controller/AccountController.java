package de.grado.customerservice.controller;

import de.grado.customerservice.dto.CreateCustomer;
import de.grado.customerservice.event.CustomerCreatedEvent;
import de.grado.customerservice.model.Account;
import de.grado.customerservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
public class AccountController
{
    private final AccountService accountService;

    @GetMapping("/getAccount/{id}")
    public List<Account> getAccounts(@PathVariable BigInteger id)
    {
        log.info("Found Customer Accounts");
        return accountService.getAccountForCustomer(id);
    }

    @PostMapping("/createAccount")
    @KafkaListener(topics = "customer", groupId = "customer-service")
    public void createAccount(@RequestBody CustomerCreatedEvent event)
    {
        log.info("Account created for customer.");
        accountService.createAccount(event);
    }
}
