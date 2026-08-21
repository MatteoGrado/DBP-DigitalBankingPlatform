package de.grado.customerservice.dto;

import de.grado.customerservice.model.CardType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDto
{
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String street;
    private String city;
    private String postalCode;
    private List<AccountDto> accounts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountDto
    {
        private String iban;
        private BigDecimal balance;
        private String currency;
        private String accountName;
        private LocalDate openedAt;
        private boolean active;
        private CardType cardType;
        private List<CardDto> cards;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardDto
    {
        private String cardNumber;
        private String cardHolderName;
        private LocalDate expiryDate;
        private String status;
        private CardType type;
    }
}
