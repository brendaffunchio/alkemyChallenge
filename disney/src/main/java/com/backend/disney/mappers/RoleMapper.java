package com.backend.disney.mappers;

import com.backend.disney.models.Role;
import com.backend.disney.modelsDTO.RoleDTO;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public Role mapDTOToEntity(RoleDTO dto) {

        Role role = new Role();
        role.setName(dto.getName().toUpperCase());

        return role;
    }
}
