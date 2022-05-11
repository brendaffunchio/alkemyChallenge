package com.backend.disney.Services.impl;

import com.backend.disney.Exception.ExceptionMessages;
import com.backend.disney.Models.Rol;
import com.backend.disney.Models.Usuario;
import com.backend.disney.ModelsDTO.UsuarioDtoResponse;
import com.backend.disney.Repositories.IUsuarioRepository;
import com.backend.disney.Security.JwtUtil;
import com.backend.disney.Services.IEmailService;
import com.backend.disney.Services.IRolService;
import com.backend.disney.Services.IUsuarioServices;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements IUsuarioServices, UserDetailsService {

    @Autowired
    IUsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    IRolService rolService;

    @Autowired
    IEmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UsuarioDtoResponse register(Usuario usuario, String rol) throws Exception {
        if (usuario == null) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_USER);
        if (usuario.getUsername().isEmpty()) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_USERNAME_USER);
        if (usuario.getPassword().isEmpty()) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_PASSWORD_USER);
        Usuario existente = repository.findByEmail(usuario.getUsername());
        if (existente != null) throw new Exception(ExceptionMessages.MSG_USERNAME_IN_USE_USER);
        Rol role = rolService.findByName(rol);
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.setRole(role);
        repository.save(usuario);
        String jwt = jwtUtil.generateToken(usuario);

        UsuarioDtoResponse response = new UsuarioDtoResponse(usuario.getId(), usuario.getUsername(), usuario.getPassword(), jwt);

        emailService.sendEmail(usuario.getUsername());
        return response;

    }

    @Override
    public Usuario login(@NotNull Usuario usuario) throws Exception {
        if (usuario == null) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_USER);
        if (usuario.getUsername().isEmpty()) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_USERNAME_USER);
        if (usuario.getPassword().isEmpty()) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_PASSWORD_USER);
        Usuario user = repository.findByEmailAndPassword(usuario.getUsername(), usuario.getPassword());
        if (user == null) throw new Exception(ExceptionMessages.MSG_USER_NOT_FOUND);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.MSG_USERNAME_NOT_FOUND);
        }

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rol);

    }
}
