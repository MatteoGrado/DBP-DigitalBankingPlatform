package de.grado.customerservice.controller;

import de.grado.customerservice.model.Account;
import de.grado.customerservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @KafkaListener(topics = "customer", groupId = "customer-service")
    public void createAccount()
    {
        //TODO: Implement Create Account Method
    }
}
