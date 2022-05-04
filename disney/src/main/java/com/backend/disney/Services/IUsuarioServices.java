package com.backend.disney.Services;

import com.backend.disney.Models.Usuario;

public interface IUsuarioServices {

   Usuario register(Usuario usuario) throws Exception;
    Usuario login(String email,String password) throws Exception;
}


