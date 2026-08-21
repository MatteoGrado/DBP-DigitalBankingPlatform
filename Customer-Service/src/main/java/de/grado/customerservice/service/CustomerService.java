package de.grado.customerservice.service;

import de.grado.customerservice.dto.CreateCustomerDto;
import de.grado.customerservice.model.Account;
import de.grado.customerservice.model.Card;
import de.grado.customerservice.model.Customer;
import de.grado.customerservice.repository.AccountRepository;
import de.grado.customerservice.repository.CardRepository;
import de.grado.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService
{
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    @Transactional
    public void createCustomer(CreateCustomerDto createCustomerDto)
    {
        Customer customer = new Customer();

        customer.setFirstName(createCustomerDto.getFirstName());
        customer.setLastName(createCustomerDto.getLastName());
        customer.setEmail(createCustomerDto.getEmail());
        customer.setPhoneNumber(createCustomerDto.getPhoneNumber());
        customer.setDateOfBirth(createCustomerDto.getDateOfBirth());
        customer.setStreet(createCustomerDto.getStreet());
        customer.setCity(createCustomerDto.getCity());
        customer.setPostalCode(createCustomerDto.getPostalCode());

        customer = customerRepository.save(customer);

        List<CreateCustomerDto.AccountDto> accountDtos = createCustomerDto.getAccounts();
        if (accountDtos == null || accountDtos.isEmpty()) {
            return;
        }

        for (CreateCustomerDto.AccountDto accountDto : accountDtos) {
            Account account = new Account();
            account.setIBAN(accountDto.getIban());
            account.setBalance(accountDto.getBalance());
            account.setCurrency(accountDto.getCurrency());
            account.setAccountName(accountDto.getAccountName());
            account.setOpenedAt(accountDto.getOpenedAt());
            account.setActive(accountDto.isActive());
            account.setCardType(accountDto.getCardType());
            account.setCustomer(customer);

            account = accountRepository.save(account);

            List<CreateCustomerDto.CardDto> cardDtos = accountDto.getCards();
            if (cardDtos == null || cardDtos.isEmpty()) {
                continue;
            }

            for (CreateCustomerDto.CardDto cardDto : cardDtos) {
                Card card = new Card();
                card.setCardNumber(cardDto.getCardNumber());
                card.setCardHolderName(cardDto.getCardHolderName());
                card.setExpiryDate(cardDto.getExpiryDate());
                card.setStatus(cardDto.getStatus());
                card.setType(cardDto.getType());
                card.setAccount(account);

                cardRepository.save(card);
            }
        }
    }

    public List<Customer> getCustomers()
    {
        log.info("Get Customers");
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long id)
    {
        log.info("Get Customer {}", id);
        return customerRepository.findById(id);
    }
}
