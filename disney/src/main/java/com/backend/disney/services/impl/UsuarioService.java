package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.UserMapper;
import com.backend.disney.models.Rol;
import com.backend.disney.models.Usuario;
import com.backend.disney.modelsDTO.UsuarioDTO;
import com.backend.disney.repositories.IUsuarioRepository;
import com.backend.disney.security.JwtUtil;
import com.backend.disney.services.IEmailService;
import com.backend.disney.services.IRolService;
import com.backend.disney.services.IUsuarioServices;
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

    private IUsuarioRepository repository;
    private BCryptPasswordEncoder encoder;
    private IRolService rolService;
    private IEmailService emailService;
    private JwtUtil jwtUtil;
    private UserMapper mapper;

    @Autowired
    public UsuarioService(IUsuarioRepository repository, BCryptPasswordEncoder encoder, IRolService rolService, IEmailService emailService, JwtUtil jwtUtil, UserMapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.rolService = rolService;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    public UsuarioDTO register(UsuarioDTO usuario) throws Exception {
        Usuario existente = repository.findByEmail(usuario.getUsername());
        if (existente != null) throw new Exception(ExceptionMessages.USERNAME_IN_USE);

        Rol role = rolService.findByName(usuario.getRol());
        String password = encoder.encode(usuario.getPassword());

        Usuario saved = repository.save(mapper.mapDTOToEntity(usuario, role, password));

        emailService.sendEmail(usuario.getUsername());
        return mapper.mapEntityToUsuarioDTOToResponse(saved);

    }

    @Override
    public Usuario login(@NotNull Usuario usuario) throws Exception {
        if (usuario == null) throw new Exception(ExceptionMessages.USER_NULL);
        if (usuario.getUsername().isEmpty()) throw new Exception(ExceptionMessages.USERNAME_EMPTY);
        if (usuario.getPassword().isEmpty()) throw new Exception(ExceptionMessages.PASSWORD_EMPTY);
        Usuario user = repository.findByEmailAndPassword(usuario.getUsername(), usuario.getPassword());
        if (user == null) throw new Exception(ExceptionMessages.USERNAME_PASSWORD_INCORRECT);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rol);

    }
}
