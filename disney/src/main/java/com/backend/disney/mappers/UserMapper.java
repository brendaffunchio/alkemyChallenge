package com.backend.disney.mappers;

import com.backend.disney.models.Rol;
import com.backend.disney.models.Usuario;
import com.backend.disney.modelsDTO.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Usuario mapDTOToEntity(UsuarioDTO dto, Rol role, String password) {

       Usuario usuario = new Usuario();
usuario.setUsername(dto.getUsername());
usuario.setPassword(password);
usuario.setRole(role);
        return usuario;
    }

    public UsuarioDTO mapEntityToUsuarioDTOToResponse(Usuario usuario) {

        UsuarioDTO dto= new UsuarioDTO();
        dto.setUsername(usuario.getUsername());
        dto.setRol(usuario.getRole().getName());
        return dto;
    }
}
