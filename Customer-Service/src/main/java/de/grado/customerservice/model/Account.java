package de.grado.customerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private BigDecimal balance;
    private String accountNumber;
    private String IBAN;
    private String BIC;
    private String status;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Cards> cards;
}
