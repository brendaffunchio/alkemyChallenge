package com.backend.disney.Services.impl;

import com.backend.disney.Exception.ExceptionMessages;
import com.backend.disney.Models.Rol;
import com.backend.disney.Repositories.IRolRepository;
import com.backend.disney.Services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService implements IRolService {

    @Autowired
    private IRolRepository repository;

    @Override
    public void create(Rol rol) throws Exception {
       Rol r=repository.findByName(rol.getName());
       if(r!=null)throw new Exception("The role is already exists");

     repository.save(rol);
    }

    @Override
    public Rol findByName(String name) throws Exception {
       if(name.isEmpty())throw new Exception(ExceptionMessages.NAME_ROL_EMPTY);
      Rol rol = repository.findByName(name);
      if(rol==null)throw new Exception(ExceptionMessages.ROLE_NOT_FOUND);

      return rol;
    }
}
