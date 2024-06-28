package com.sogeti.menu.app.mapper;

import com.sogeti.menu.app.persistence.entities.UserEntity;
import com.sogeti.menu.app.rest.dtos.UserDto;
import com.sogeti.menu.app.rest.requests.RegistrationRequest;
import com.sogeti.menu.app.rest.responses.UserResponse;

public class UserMapper {

    public static UserDto fromRequestToDto(RegistrationRequest registrationRequest) {
        if(registrationRequest == null) {
            return null;
        }
        return UserDto.builder()
                .fullName(registrationRequest.fullName())
                .email(registrationRequest.email())
                .password(registrationRequest.password())
                .build();
    }

    public static UserEntity fromDtoToEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return UserEntity.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public static UserDto fromEntityToDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDto.builder()
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .build();
    }

    public static UserResponse fromDtoToResponse(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return UserResponse.builder()
                .id(userDto.getId())
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .build();
    }

}
