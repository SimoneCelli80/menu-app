package com.sogeti.menu.app.rest.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public record UserRequest(

        @NotNull(message = "Please enter your full name.")
        @NotBlank(message = "Please enter your full name.")
        String fullName,
        @Email
        @NotNull(message = "Please enter your email.")
        @NotBlank(message = "Please enter your email.")
        String email,
        @NotNull(message = "Please enter a valid password.")
        @NotBlank(message = "Please enter a valid password.")
        String password

) {
}
