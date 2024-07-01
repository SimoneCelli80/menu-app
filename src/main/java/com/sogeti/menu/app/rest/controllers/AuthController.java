package com.sogeti.menu.app.rest.controllers;

import com.sogeti.menu.app.mapper.LoginMapper;
import com.sogeti.menu.app.mapper.UserMapper;
import com.sogeti.menu.app.rest.dtos.LoginDto;
import com.sogeti.menu.app.rest.dtos.UserDto;
import com.sogeti.menu.app.rest.requests.LoginRequest;
import com.sogeti.menu.app.rest.requests.RegistrationRequest;
import com.sogeti.menu.app.rest.responses.LoginResponse;
import com.sogeti.menu.app.rest.responses.UserResponse;
import com.sogeti.menu.app.service.AuthService;
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
        UserDto userDto = UserMapper.fromRequestToDto(registrationRequest);
        authService.registerUser(userDto);
        return UserMapper.fromDtoToResponse(userDto);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@Validated @RequestBody LoginRequest loginRequest) {
        LoginDto loginDto = LoginMapper.fromRequestToDto(loginRequest);
        String jwt = authService.loginUser(loginDto);
        return new LoginResponse(jwt);
    }
}
