package com.backend.disney.Services;

import com.backend.disney.Models.Usuario;
import com.backend.disney.ModelsDTO.UsuarioDtoResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUsuarioServices {

   UsuarioDtoResponse register(Usuario usuario, String rol) throws Exception;
   Usuario login(Usuario usuario) throws Exception;

}


