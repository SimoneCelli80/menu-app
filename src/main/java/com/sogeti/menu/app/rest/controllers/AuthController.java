package com.sogeti.menu.app.rest.controllers;

import com.sogeti.menu.app.rest.responses.UserResponse;
import com.sogeti.menu.app.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registration")
    public UserResponse registerUser(@Validated @RequestBody RegistrationRequest registrationRequest) {

    }
}
