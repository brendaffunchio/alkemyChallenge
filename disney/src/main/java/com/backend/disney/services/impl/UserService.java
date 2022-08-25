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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserServices{

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

        emailService.sendEmail(saved.getUsername());
        return mapper.mapEntityToUserDTO(saved);

    }

    @Override
    public User getByEmail(String email) {
        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND);
        }
return user;
    }

}
