package com.backend.disney.Services;

import com.backend.disney.Models.Rol;

public interface IRolService {

    void create(Rol rol) throws Exception;
    Rol findByName(String name) throws Exception;
}
