package com.backend.disney.Controllers;

import com.backend.disney.Models.Genero;
import com.backend.disney.Models.Pelicula;
import com.backend.disney.Services.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genero")
public class GeneroController {

    @Autowired
    private IGeneroService service;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Genero createGenre(@RequestBody Genero genero) throws Exception {
        try {
            return service.createGenero(genero);
        } catch (Exception e) {
            return null;
        }
    }

}
