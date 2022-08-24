package com.backend.disney.controllers;

import com.backend.disney.modelsDTO.PeliculaDTOCompleta;
import com.backend.disney.services.IPeliculaService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("movies")
public class PeliculaController {


    private IPeliculaService service;

    @Autowired
    public PeliculaController(IPeliculaService service) {
        this.service = service;

    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createMovie(@ModelAttribute PeliculaDTOCompleta pelicula,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("idPersonajes") @Nullable String[] idPersonajes) {
        try {
            return new ResponseEntity<>(service.createPelicula(pelicula, file, idPersonajes), HttpStatus.CREATED);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping(path = "/edit")
    public ResponseEntity<?> editMovie(@ModelAttribute PeliculaDTOCompleta pelicula, @RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        try {
            return new ResponseEntity<>(service.updatePelicula(pelicula, id, file), HttpStatus.OK);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<?> deleteMovie(@RequestParam(value = "id", required = false) String id) {
        try {

            service.deletePelicula(id);
            return new ResponseEntity<>("Movie deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getDetails")
    public ResponseEntity<?> getMovieDetails(@RequestParam(value = "id", required = false) String id) {
        try {
            return new ResponseEntity<>(service.getDetailsPelicula(id), HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @GetMapping(path = "/")
    public ResponseEntity<List<?>> getMovies(@RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "genre_id", required = false) String genre_id,
                                             @RequestParam(value = "order", required = false) String order) {

        return new ResponseEntity<>(service.searchPeliculas(name, genre_id, order), HttpStatus.OK);
    }

    @PostMapping(path = "/{idMovie}/characters/{idCharacter}")
    public ResponseEntity<?> addCharacter(@PathVariable String idMovie, @PathVariable String idCharacter) {

        try {
            service.addPersonaje(idMovie, idCharacter);
            return new ResponseEntity<>("Character added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{movieId}/characters/{characterId}")
    public ResponseEntity<?> deleteCharacter(@PathVariable String movieId, @PathVariable String characterId, HttpServletResponse httpServletResponse) {

        try {
            service.removePersonaje(movieId, characterId);
            return new ResponseEntity<>("Character removed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}