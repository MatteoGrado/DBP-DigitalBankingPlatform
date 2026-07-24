package de.grado.customerservice.event;

import de.grado.customerservice.model.Account;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Getter
public record CustomerCreatedEvent(
        BigInteger customerId,
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
