package de.grado.customerservice.repository;

import de.grado.customerservice.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>
{
    Optional<Card> findByCardNumber(String cardNumber);

    List<Card> findByAccountId(Long accountId);
}
