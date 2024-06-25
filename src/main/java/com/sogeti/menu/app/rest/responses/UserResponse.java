package com.sogeti.menu.app.rest.responses;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {

    private long id;
    private String fullName;
    private String email;

}
