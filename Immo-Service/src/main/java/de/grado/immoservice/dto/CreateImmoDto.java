package de.grado.immoservice.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
public class CreateImmoDto
{
    private String address;
    private String zipCode;
    private String city;
    private String currentOwner;
    private LocalDate createdAt;
    private BigDecimal price;

    private List<MultipartFile> propertyImages;
}
