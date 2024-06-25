package com.sogeti.menu.app.rest.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    private long id;
    private String fullName;
    private String email;
    private String password;

}
