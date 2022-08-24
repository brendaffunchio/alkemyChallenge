package com.backend.disney.services;

import com.backend.disney.models.Rol;
import com.backend.disney.modelsDTO.RoleDTO;

public interface IRolService {

    void create(RoleDTO rol) throws Exception;
    Rol findByName(String name) throws Exception;
}
