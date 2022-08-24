package com.backend.disney.services;

import com.backend.disney.models.Usuario;
import com.backend.disney.modelsDTO.UsuarioDTO;

public interface IUsuarioServices {

   UsuarioDTO register(UsuarioDTO usuario) throws Exception;
   Usuario login(Usuario usuario) throws Exception;

}


