package com.sogeti.menu.app.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private long id;
    @NotNull
    @NotBlank
    private String fullName;
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String password;


}
