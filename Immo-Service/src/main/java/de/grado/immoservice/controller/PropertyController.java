package de.grado.immoservice.controller;

import de.grado.immoservice.dto.CreateImmoDto;
import de.grado.immoservice.model.Property;
import de.grado.immoservice.service.ImmoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
@Slf4j
public class PropertyController
{
    private final ImmoService immoService;

    @PostMapping("/createProperty")
    public void createPropertyListing(@RequestBody CreateImmoDto createImmoDto)
    {
        immoService.createPropertyInsert(createImmoDto);
    }

    @GetMapping("/listPropertys")
    public List<Property> getListProperty()
    {
        log.info("Listed all Properties");
        return immoService.listProperty();
    }

    @GetMapping("/getProperty/{id}")
    public Property getPropertyById(@PathVariable("id") BigInteger id)
    {
        log.info("Getting property with id {}", id);
        return immoService.getProperty(id);
    }
}
