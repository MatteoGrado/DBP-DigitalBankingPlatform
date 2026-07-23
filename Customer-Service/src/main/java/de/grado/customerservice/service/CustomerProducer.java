package de.grado.customerservice.service;

import de.grado.customerservice.event.CustomerCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerProducer
{
    private final KafkaTemplate<String, CustomerCreatedEvent> kafkaTemplate;

    public void sendCustomerCreated(CustomerCreatedEvent event)
    {
        kafkaTemplate.send("customer-created", event);
    }
}
