package de.grado.customerservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Account> accounts;

    private String firstName;
    private String lastName;

    private String address;
    private String houseNumber;
    private String zipCode;
    private String city;
    private String state;

    private String email;
    private String phoneNumber;

    private String IBAN;

    //TODO: Include File Upload
}
