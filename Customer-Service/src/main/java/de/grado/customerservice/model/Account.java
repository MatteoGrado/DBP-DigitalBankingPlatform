package de.grado.customerservice.model;

import de.grado.customerservice.dto.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private BigDecimal balance;
    private BigInteger accountNumber;
    private String IBAN;
    private String BIC;
    private AccountStatus status;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Cards> cards;
}
