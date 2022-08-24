package com.backend.disney.services;

import com.backend.disney.models.Role;
import com.backend.disney.modelsDTO.RoleDTO;

public interface IRoleService {

    void create(RoleDTO dto) throws Exception;
    Role findByName(String name) throws Exception;
}
