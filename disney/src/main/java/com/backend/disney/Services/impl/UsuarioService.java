package com.backend.disney.Services.impl;

import com.backend.disney.Models.Usuario;
import com.backend.disney.Repositories.IUsuarioRepository;
import com.backend.disney.Services.IEmailService;
import com.backend.disney.Services.IUsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioServices {

    @Autowired
    IUsuarioRepository repository;

    @Autowired
    IEmailService emailService;

    @Override
    public Usuario register(Usuario usuario) throws Exception {
        if (usuario != null) {
            repository.save(usuario);
           emailService.sendEmail(usuario.getUsername());
           return usuario;
        } else throw new Exception("Cannot create user");
    }

    @Override
    public Usuario login(String email, String password) throws Exception {
        if (email != null && password != null) {
            System.out.println("hola 2");
            Usuario usuario = repository.findByUsernameAndPassword(email, password);
            return usuario;
        } else throw new Exception("Cannot login");

    }
}
