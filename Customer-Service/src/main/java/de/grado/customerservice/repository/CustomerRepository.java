package de.grado.customerservice.repository;

import de.grado.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    List<Customer> findByName(String firstName, String lastName);

    Customer findById(BigInteger id);
}
