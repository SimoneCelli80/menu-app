package com.sogeti.menu.app.service;

import com.sogeti.menu.app.mapper.UserMapper;
import com.sogeti.menu.app.persistence.entities.UserEntity;
import com.sogeti.menu.app.persistence.repositories.UsersRepository;
import com.sogeti.menu.app.rest.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntityList = usersRepository.findAll();
        return userEntityList.stream()
                .map(UserMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(long id) {
        UserEntity userEntity = usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorru, user-password combination not valid."));
        return UserMapper.fromEntityToDto(userEntity);
    }
}
