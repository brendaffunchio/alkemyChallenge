package com.backend.disney.controllers;

import com.backend.disney.modelsDTO.MovieDTOComplete;
import com.backend.disney.services.IMovieService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("movies")
public class MovieController {

    private IMovieService service;

    @Autowired
    public MovieController(IMovieService service) {
        this.service = service;

    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createMovie(@Valid @ModelAttribute MovieDTOComplete dto,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "idCharacters",required = false) String[] idCharacters) {
        try {
            return new ResponseEntity<>(service.create(dto, file, idCharacters), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> editMovie(@Valid @ModelAttribute MovieDTOComplete dto, @RequestParam("id") String id, @RequestParam(value = "file") MultipartFile file) {
        try {
            return new ResponseEntity<>(service.update(dto, id, file), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteMovie(@RequestParam("id") String id) {
        try {

            service.delete(id);
            return new ResponseEntity<>("Movie deleted successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getDetails")
    public ResponseEntity<?> getMovieDetails(@RequestParam(value = "id") String id) {
        try {
            return new ResponseEntity<>(service.getDetailsMovie(id), HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping(path = "/")
    public ResponseEntity<List<?>> getMovies(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "genre_id", required = false) String genre_id,
                                             @RequestParam(value = "order", required = false) String order) {

        return new ResponseEntity<>(service.searchMovies(name, genre_id, order), HttpStatus.OK);
    }

    @PostMapping(path = "/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<?> addCharacter(@PathVariable String idMovie, @PathVariable String idCharacter) {

        try {
            service.addCharacter(idMovie, idCharacter);
            return new ResponseEntity<>("Character added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{movieId}/characters/{characterId}")
    public ResponseEntity<?> deleteCharacter(@PathVariable String movieId, @PathVariable String characterId) {

        try {
            service.removeCharacter(movieId, characterId);
            return new ResponseEntity<>("Character removed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}