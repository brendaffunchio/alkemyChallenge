package com.backend.disney.controllers;

import com.backend.disney.models.Rol;
import com.backend.disney.modelsDTO.RoleDTO;
import com.backend.disney.services.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/role")
public class RolController {


    IRolService service;

    @Autowired
    public RolController(IRolService service) {
        this.service = service;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createRole(@RequestBody RoleDTO rol) {
        try {
            service.create(rol);
            return new ResponseEntity<>("Role created successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
