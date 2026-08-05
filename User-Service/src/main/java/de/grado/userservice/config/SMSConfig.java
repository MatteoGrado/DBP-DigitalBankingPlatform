package de.grado.userservice.config;

import com.vonage.client.VonageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SMSConfig
{
    @Bean
    public VonageClient vonageClient(
            @Value("${SMS_API_KEY:}") String apiKey,
            @Value("${SMS_PROVIDER_SECRET:}") String apiSecret) {

        return VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();
    }
}
