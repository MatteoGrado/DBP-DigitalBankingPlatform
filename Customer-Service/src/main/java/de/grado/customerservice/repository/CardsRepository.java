package de.grado.customerservice.repository;

import de.grado.customerservice.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Cards, BigInteger>
{
    List<Cards> findAllByAccount_Id(BigInteger accountId);
    Optional<Cards> findByCardNumber(String cardNumber);
}
