package com.backend.disney.controllers;

import com.backend.disney.modelsDTO.RoleDTO;
import com.backend.disney.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
public class RoleController {


    IRoleService service;

    @Autowired
    public RoleController(IRoleService service) {
        this.service = service;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleDTO dto) {
        try {
            service.create(dto);
            return new ResponseEntity<>("Role created successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
