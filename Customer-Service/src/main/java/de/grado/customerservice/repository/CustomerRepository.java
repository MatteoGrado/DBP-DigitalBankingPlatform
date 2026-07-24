package de.grado.customerservice.repository;

import de.grado.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, BigInteger>
{
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
