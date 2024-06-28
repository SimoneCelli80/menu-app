package com.sogeti.menu.app.persistence.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public enum RoleEnum {
    USER("User"),
    ADMIN("Admin");

    private final String role;

    RoleEnum(String role){
        this.role = role;
    }

    public static RoleEnum fromRole(String role){
        for (RoleEnum roleEnum : RoleEnum.values()){
            if (roleEnum.getRole().equalsIgnoreCase(role)){
                return roleEnum;
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("No such role (%s) exists within the system", role));
    }

}

