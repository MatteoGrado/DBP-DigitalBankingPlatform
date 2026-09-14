package de.grado.immoservice.service;

import de.grado.immoservice.config.S3Properties;
import de.grado.immoservice.dto.CreateImmoDto;
import de.grado.immoservice.model.Property;
import de.grado.immoservice.model.PropertyImages;
import de.grado.immoservice.repository.PropertyImagesRepository;
import de.grado.immoservice.repository.PropertyRepository;
import io.sentry.Sentry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImmoService
{
    private final PropertyRepository propertyRepository;
    private final PropertyImagesRepository propertyImagesRepository;
    private final S3Client s3Client;
    private final S3Properties s3Properties;

    @Transactional
    public void createPropertyInsert(CreateImmoDto createImmoDto)
    {
        Property property = new Property();

        property.setAddress(createImmoDto.getAddress());
        property.setZipCode(createImmoDto.getZipCode());
        property.setCity(createImmoDto.getCity());
        property.setCurrentOwner(createImmoDto.getCurrentOwner());
        property.setCreatedAt(LocalDate.now());
        property.setPrice(createImmoDto.getPrice());

        Property savedProperty = propertyRepository.save(property);
        List<String> imageUrls = uploadImages(createImmoDto);

        try {
            for (String imageUrl : imageUrls) {
                PropertyImages propertyImage = new PropertyImages();
                propertyImage.setProperty(savedProperty);
                propertyImage.setImageUrl(imageUrl);
                propertyImagesRepository.save(propertyImage);
            }
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("Error saving property images", e);

            throw new RuntimeException("Something went wrong!", e);
        }
    }

    public List<String> uploadImages(CreateImmoDto createImmoDto)
    {
        List<MultipartFile> files = createImmoDto.getPropertyImages();
        List<String> imageUrls = new ArrayList<>();

        if (files == null || files.isEmpty()) {
            return imageUrls;
        }

        try {
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String filename = UUID.randomUUID() + "-" + originalFilename;
                String storageKey = "images/" + createImmoDto.getAddress() + "/" + filename;

                PutObjectRequest request = PutObjectRequest.builder().bucket(s3Properties.bucketName()).key(storageKey)
                        .contentType(file.getContentType()).build();

                s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
                log.info("Image uploaded: {}", filename);

                String url = s3Client.utilities()
                        .getUrl(builder ->
                                builder.bucket(s3Properties.bucketName())
                                        .key(storageKey))
                        .toExternalForm();

                imageUrls.add(url);
                log.info("Image URL created: {}", url);
            }
            return imageUrls;
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("Error uploading images", e);

            throw new RuntimeException("Something went wrong!", e);
        }
    }

    public List<Property> listProperty()
    {
        log.info("Got all Property");
        return propertyRepository.findAll();
    }

    public Property getProperty(BigInteger id)
    {
    }
}
