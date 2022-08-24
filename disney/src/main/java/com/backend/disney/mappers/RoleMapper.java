package com.backend.disney.mappers;

import com.backend.disney.models.Rol;
import com.backend.disney.modelsDTO.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Rol mapDTOToEntity(RoleDTO dto) {

        Rol rol = new Rol();
        rol.setName(dto.getName());

        return rol;
    }
}
