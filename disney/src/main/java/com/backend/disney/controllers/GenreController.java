package com.backend.disney.controllers;

import com.backend.disney.modelsDTO.GenreDTO;
import com.backend.disney.services.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;


@RestController
@RequestMapping("/genre")
public class GenreController {


    private IGenreService service;
    @Autowired
    public GenreController(IGenreService service){
        this.service=service;
    }
    @PostMapping(path = "/create")
    public ResponseEntity<?> createGenre(@Valid @ModelAttribute GenreDTO dto, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(service.create(dto, file),HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
