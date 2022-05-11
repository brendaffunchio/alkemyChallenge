package com.backend.disney.Services;

import com.backend.disney.Models.Rol;

public interface IRolService {

    Rol findByName(String name) throws Exception;
}
