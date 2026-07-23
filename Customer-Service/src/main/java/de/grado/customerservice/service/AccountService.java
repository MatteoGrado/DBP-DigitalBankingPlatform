package de.grado.customerservice.service;

import de.grado.customerservice.model.Account;
import de.grado.customerservice.repository.AccountRepository;
import de.grado.customerservice.repository.CardsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService
{
    private final AccountRepository accountRepository;
    private final CardsRepository cardsRepository;

    public List<Account> getAccountForCustomer(BigInteger id)
    {
        return accountRepository.findById(id);
    }
}
