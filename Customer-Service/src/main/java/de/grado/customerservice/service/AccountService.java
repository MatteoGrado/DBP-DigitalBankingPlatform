package de.grado.customerservice.service;

import de.grado.customerservice.dto.AccountStatus;
import de.grado.customerservice.event.CustomerCreatedEvent;
import de.grado.customerservice.model.Account;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repository.AccountRepository;
import de.grado.customerservice.repository.CardsRepository;
import de.grado.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService
{
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CardsRepository cardsRepository;
    private static final String BIC = "SOLADES4ST";

    public List<Account> getAccountForCustomer(BigInteger id)
    {
        return accountRepository.findById(id);
    }

    public String createAccount(CustomerCreatedEvent event)
    {
        Customer customer = customerRepository.findById(event.customerId());

        Account account = new Account();

        account.setAccountNumber(event.customerId());
        account.setCustomer(customer);
        account.setIBAN(event.IBAN());
        account.setBIC(BIC);
        account.setBalance(BigDecimal.valueOf(0.00));
        account.setStatus(AccountStatus.CREATED);

        accountRepository.save(account);
        return "Account Successfully created.";
    }
}
