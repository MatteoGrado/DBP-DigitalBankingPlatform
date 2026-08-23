package de.grado.immoservice.service;

import de.grado.immoservice.repository.PropertyImagesRepository;
import de.grado.immoservice.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImmoService
{
    private final PropertyRepository propertyRepository;
    private final PropertyImagesRepository propertyImagesRepository;
    private final S3Client s3Client;

    @Transactional
    public void createPropertyInsert()
    {
    }
}
