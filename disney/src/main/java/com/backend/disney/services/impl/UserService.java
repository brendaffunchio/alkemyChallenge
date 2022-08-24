package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.UserMapper;
import com.backend.disney.models.Role;
import com.backend.disney.models.User;
import com.backend.disney.modelsDTO.UserDTO;
import com.backend.disney.repositories.IUserRepository;
import com.backend.disney.security.JwtUtil;
import com.backend.disney.services.IEmailService;
import com.backend.disney.services.IRoleService;
import com.backend.disney.services.IUserServices;
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
public class UserService implements IUserServices, UserDetailsService {

    private IUserRepository repository;
    private BCryptPasswordEncoder encoder;
    private IRoleService rolService;
    private IEmailService emailService;
    private JwtUtil jwtUtil;
    private UserMapper mapper;

    @Autowired
    public UserService(IUserRepository repository, BCryptPasswordEncoder encoder, IRoleService rolService, IEmailService emailService, JwtUtil jwtUtil, UserMapper mapper) {
        this.repository = repository;
        this.encoder = encoder;
        this.rolService = rolService;
        this.emailService = emailService;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    public UserDTO register(UserDTO dto) throws Exception {
        User found = repository.findByEmail(dto.getUsername());
        if (found != null) throw new Exception(ExceptionMessages.USERNAME_IN_USE);

        Role role = rolService.findByName(dto.getRole());
        String password = encoder.encode(dto.getPassword());

        User saved = repository.save(mapper.mapDTOToEntity(dto, role, password));

        emailService.sendEmail(dto.getUsername());
        return mapper.mapEntityToMovieDTOToResponse(saved);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }

        List<GrantedAuthority> rol = new ArrayList<>();
        rol.add(new SimpleGrantedAuthority(user.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rol);

    }
}
