package de.grado.immoservice.service;

import de.grado.immoservice.config.S3Properties;
import de.grado.immoservice.dto.CreateImmoDto;
import de.grado.immoservice.event.PropertyEvent;
import de.grado.immoservice.model.Property;
import de.grado.immoservice.repository.PropertyImagesRepository;
import de.grado.immoservice.repository.PropertyRepository;
import io.sentry.Sentry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDate;
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
    private final KafkaTemplate<Object, PropertyEvent> kafkaTemplate;

    @Transactional
    @KafkaListener(topicPattern = "property-images-topic")
    public void createPropertyInsert(CreateImmoDto createImmoDto, PropertyEvent propertyEvent)
    {
        Property property = new Property();

        property.setAddress(createImmoDto.getAddress());
        property.setZipCode(createImmoDto.getZipCode());
        property.setCity(createImmoDto.getCity());
        property.setCurrentOwner(createImmoDto.getCurrentOwner());
        property.setCreatedAt(LocalDate.now());
        property.setPrice(createImmoDto.getPrice());
        //TODO: How to get the links of the images in here

        propertyRepository.save(property);
    }

    public String uploadImages(CreateImmoDto createImmoDto)
    {
        List<MultipartFile> files = createImmoDto.getPropertyImages();

        try {
            for (MultipartFile file : files) {

                String originalFilename = file.getOriginalFilename();
                String filename = UUID.randomUUID() + "-" + originalFilename;
                String storageKey = "images/" + createImmoDto.getAddress() + "/" + filename;

                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket(s3Properties.bucketName())
                        .key(storageKey).contentType(file.getContentType())
                        .build();

                s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
                log.info("Image uploaded: {}", filename);

                String url = s3Client.utilities()
                        .getUrl(builder -> builder
                                .bucket(s3Properties.bucketName())
                                .key(storageKey))
                        .toExternalForm();

                kafkaTemplate.send("property-images-topic", new PropertyEvent(url));
                log.info("URL send via Kafka: {}", url);
            }
            return "SUCCESS";
        } catch (Exception e) {
            Sentry.captureException(e);
            log.error("Error uploading images", e);

            throw new RuntimeException("Something went wrong!", e);
        }
    }
}
