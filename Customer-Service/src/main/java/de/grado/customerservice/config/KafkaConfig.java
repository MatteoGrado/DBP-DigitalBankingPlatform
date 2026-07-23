package de.grado.customerservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfig
{

    @Setter
    private String bootstrapServers;
    private final Consumer consumer = new Consumer();
    private final Producer producer = new Producer();

    public String getBootstrapServers()
    {
        return bootstrapServers;
    }

    public Consumer getConsumer()
    {
        return consumer;
    }

    public Producer getProducer()
    {
        return producer;
    }

    @Setter
    @Getter
    public static class Consumer
    {
        private String groupId;
        private String autoOffsetReset;

    }

    @Setter
    @Getter
    public static class Producer
    {
        private String acks;

    }

}
