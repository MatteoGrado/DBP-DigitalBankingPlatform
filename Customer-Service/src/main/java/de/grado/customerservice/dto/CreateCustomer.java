package de.grado.customerservice.dto;

import de.grado.customerservice.model.Account;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateCustomer
{
    private List<Account> accounts;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String state;
    private String email;
    private String phoneNumber;
    private String IBAN;
}
