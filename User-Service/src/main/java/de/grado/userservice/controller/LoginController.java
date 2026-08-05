package de.grado.userservice.controller;

import de.grado.userservice.dto.UserLoginRequest;
import de.grado.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController
{
    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest)
    {
        userService.login(userLoginRequest);

        return ResponseEntity.ok()
                .body("Successfully logged in!");
    }
}
