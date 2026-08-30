package de.grado.immoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "property")
@Data
public class Property
{
    @Id
    private Long id;

    private String address;
    private String zipCode;
    private String city;

    private String currentOwner;
    private LocalDate createdAt;
    private BigDecimal price;
    private String imagesUrl;

    @OneToMany(mappedBy = "property")
    private List<PropertyImages> propertyImages = new ArrayList<>();
}
