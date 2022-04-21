package com.backend.disney.Services;

import com.backend.disney.Models.Usuario;
import com.backend.disney.Repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioServices{

    @Autowired
    IUsuarioRepository repository;


    @Override
    public Usuario register(Usuario usuario) throws Exception {

        if(usuario!=null){
            repository.save(usuario);
            return usuario;
        }else throw new Exception("Cannot create user");
    }

    @Override
    public Usuario login(String email, String password) throws Exception {
      if(email!=null && password!=null) {

          Usuario usuario = repository.findByUsernameAndPassword(email, password);
          return usuario;
      }else throw new Exception("Cannot login");

    }
}
