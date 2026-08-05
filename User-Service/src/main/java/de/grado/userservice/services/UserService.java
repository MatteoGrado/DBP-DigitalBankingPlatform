package de.grado.userservice.services;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.messages.TextMessage;
import de.grado.userservice.dto.UserLoginRequest;
import de.grado.userservice.event.UserLoginEvent;
import de.grado.userservice.model.User;
import de.grado.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService
{
    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserLoginEvent> kafkaTemplate;
    private static final SecureRandom RANDOM = new SecureRandom();
    private final VonageClient vonageClient;

    @Value("${SMS_PROVIDER_FROM}")
    private String from;

    public void login(UserLoginRequest userLoginRequest)
    {
        User userLogin = userRepository.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User with this email was not found: " + userLoginRequest.getEmail()));

        if (!userLogin.getPassword().equals(userLoginRequest.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        UserLoginEvent event = new UserLoginEvent(
                userLogin.getEmail(),
                userLogin.getPhone()
        );
        kafkaTemplate.send("user-login-topic", event);
    }

    @KafkaListener(topics = "user-login-topic")
    public void send2FAToken(UserLoginEvent event)
    {
        TextMessage message = new TextMessage(
                from,
                event.getPhone(),
                "Ihr Bestätigungscode lautet: " + generateVerifyCode()
        );

        vonageClient.getSmsClient().submitMessage(message);
    }

    public String generateVerifyCode()
    {
        int code = RANDOM.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
