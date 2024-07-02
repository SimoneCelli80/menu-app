package com.sogeti.menu.app.rest.controllers;

import com.sogeti.menu.app.mapper.UserMapper;
import com.sogeti.menu.app.persistence.entities.UserEntity;
import com.sogeti.menu.app.rest.dtos.UserDto;
import com.sogeti.menu.app.rest.responses.UserResponse;
import com.sogeti.menu.app.service.UsersService;
import org.apache.catalina.UserDatabase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    UserDatabase userDatabase;
    UsersService usersService;

    public UsersController(UsersService userService) {
        this.usersService = userService;
    }

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserDto> userDtoList = usersService.getAllUsers();
        return new ResponseEntity(userDtoList.stream().map(UserMapper::fromDtoToResponse), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        UserDto userDto = usersService.getUserById(id);
        return new ResponseEntity(UserMapper.fromDtoToResponse(userDto), HttpStatus.OK);
    }
}
