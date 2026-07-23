package de.grado.customerservice.event;

import de.grado.customerservice.model.Account;

import java.time.LocalDate;
import java.util.List;

public record CustomerCreatedEvent(
        List<Account> accounts,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String address,
        String houseNumber,
        String zipCode,
        String city,
        String state,
        String email,
        String phoneNumber,
        String IBAN
)
{
}
