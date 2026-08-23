package de.grado.immoservice.controller;

import de.grado.immoservice.dto.CreateImmoDto;
import de.grado.immoservice.service.ImmoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        String result = immoService.uploadImages(createImmoDto);
    }
}
