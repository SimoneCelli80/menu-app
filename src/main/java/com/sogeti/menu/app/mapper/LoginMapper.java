package com.sogeti.menu.app.mapper;

import com.sogeti.menu.app.rest.dtos.LoginDto;
import com.sogeti.menu.app.rest.requests.LoginRequest;
import com.sogeti.menu.app.rest.responses.LoginResponse;

public class LoginMapper {

    public static LoginDto fromRequestToDto(LoginRequest loginRequest) {
        if (loginRequest == null) {
            return null;
        }

        return LoginDto.builder()
                .email(loginRequest.email())
                .password(loginRequest.password())
                .build();
    }


}
