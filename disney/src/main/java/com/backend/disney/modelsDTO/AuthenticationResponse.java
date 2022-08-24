package com.backend.disney.modelsDTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String jwt;
}
