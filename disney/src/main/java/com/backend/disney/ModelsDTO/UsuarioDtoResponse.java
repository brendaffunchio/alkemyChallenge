package com.backend.disney.ModelsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UsuarioDtoResponse {

    private Integer id;
    private String username;
    private String password;
    private String jwt;

    public UsuarioDtoResponse(Integer id,String username,String password, String jwt){
        this.id=id;
        this.username=username;
        this.password=password;
        this.jwt=jwt;
    }
}