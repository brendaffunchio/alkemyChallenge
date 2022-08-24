package com.backend.disney.controllers;

import com.backend.disney.models.Genero;
import com.backend.disney.modelsDTO.GeneroDTO;
import com.backend.disney.services.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/genre")
public class GeneroController {


    private IGeneroService service;
    @Autowired
    public GeneroController( IGeneroService service){
        this.service=service;
    }
    @PostMapping(path = "/create")
    public ResponseEntity<?> createGenre(@ModelAttribute GeneroDTO genero, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(service.createGenero(genero, file),HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
