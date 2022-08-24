package com.backend.disney.controllers;

import com.backend.disney.modelsDTO.CharacterDTOComplete;
import com.backend.disney.services.ICharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("characters")
public class CharacterController {


    private ICharacterService service;

    @Autowired
    public CharacterController(ICharacterService service) {
        this.service = service;

    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createCharacter(@ModelAttribute CharacterDTOComplete dto, @RequestParam("file") MultipartFile file) {
        try {

            return new ResponseEntity<>(service.create(dto, file), HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> editCharacter(@Valid @ModelAttribute CharacterDTOComplete dto, @RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(service.update(dto, id, file), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteCharacter(@RequestParam(value = "id") String id) {
        try {
            service.delete(id);
            return new ResponseEntity<>("Character deleted successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getDetails")
    public ResponseEntity<?> getCharacterDetails(@RequestParam(value = "id") String id) {
        try {

            return new ResponseEntity<>(service.getDetails(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity<?> getCharacters(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "age", required = false) Integer age, @RequestParam(value = "weight", required = false) Integer weight, @RequestParam(value = "movies", required = false) String movies) throws Exception {
        try {
            return new ResponseEntity<>(service.searchCharacters(name, age, weight, movies), HttpStatus.OK);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}