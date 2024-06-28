package com.sogeti.menu.app.service;

import com.sogeti.menu.app.configuration.JwtUtil;
import com.sogeti.menu.app.mapper.UserMapper;
import com.sogeti.menu.app.persistence.entities.UserEntity;
import com.sogeti.menu.app.persistence.repositories.UsersRepository;
import com.sogeti.menu.app.rest.dtos.LoginDto;
import com.sogeti.menu.app.rest.dtos.UserDto;
import com.sogeti.menu.app.rest.responses.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto registerUser(UserDto userDto) {
        if (usersRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This email is already in use, please choose a different one.");
        } else {
            String hashPassword = passwordEncoder.encode(userDto.getPassword());
            userDto.setPassword(hashPassword);
            usersRepository.save(UserMapper.fromDtoToEntity(userDto));
            return userDto;
        }
    }

    public String loginUser(LoginDto loginDto) {
        UserEntity userEntity = usersRepository.findByEmail(loginDto.getEmail())
                .filter(user -> passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Please, enter a valid email password combination."));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        String jwt =jwtUtil.generateToken(userEntity, generateExtraClaims(userEntity));
        return jwt; //the controller will return LoginResponse(jwt)
    }

    private Map<String, Object> generateExtraClaims(UserEntity userEntity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", userEntity.getFullName());
        extraClaims.put("email", userEntity.getEmail());
        return extraClaims;
    }
}
