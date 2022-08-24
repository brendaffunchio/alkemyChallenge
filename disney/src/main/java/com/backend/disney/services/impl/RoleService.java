package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.RoleMapper;
import com.backend.disney.models.Role;
import com.backend.disney.modelsDTO.RoleDTO;
import com.backend.disney.repositories.IRoleRepository;
import com.backend.disney.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {


    private IRoleRepository repository;
    private RoleMapper mapper;

    @Autowired
    public RoleService(IRoleRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(RoleDTO dto) throws Exception {
        Role r = repository.findByName(dto.getName());
        if (r != null) throw new Exception(ExceptionMessages.ROLE_EXISTS);

        repository.save(mapper.mapDTOToEntity(dto));
    }

    @Override
    public Role findByName(String name) throws Exception {

        Role role = repository.findByName(name);
        if (role == null) throw new Exception(ExceptionMessages.ROLE_NOT_FOUND);

        return role;
    }
}
