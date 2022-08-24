package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.RoleMapper;
import com.backend.disney.models.Rol;
import com.backend.disney.modelsDTO.RoleDTO;
import com.backend.disney.repositories.IRolRepository;
import com.backend.disney.services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService implements IRolService {


    private IRolRepository repository;
    private RoleMapper mapper;

    @Autowired
    public RolService(IRolRepository repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(RoleDTO rol) throws Exception {
        Rol r = repository.findByName(rol.getName());
        if (r != null) throw new Exception(ExceptionMessages.ROLE_EXISTS);

        repository.save(mapper.mapDTOToEntity(rol));
    }

    @Override
    public Rol findByName(String name) throws Exception {

        Rol rol = repository.findByName(name);
        if (rol == null) throw new Exception(ExceptionMessages.ROLE_NOT_FOUND);

        return rol;
    }
}
