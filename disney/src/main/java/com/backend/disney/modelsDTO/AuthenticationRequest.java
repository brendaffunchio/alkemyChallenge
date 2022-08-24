package com.backend.disney.modelsDTO;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "username cannot be blank")
    @Email(message = "invalid email format")
    private String username;
    @NotBlank(message="password cannot be blank")
    private String password;
}