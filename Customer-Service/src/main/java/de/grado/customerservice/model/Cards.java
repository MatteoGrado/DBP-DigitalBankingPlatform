package de.grado.customerservice.model;

import de.grado.customerservice.dto.CardForm;
import de.grado.customerservice.dto.CardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Cards
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private CardType cardType;
    private CardForm cardForm;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    private LocalDate expirationDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
