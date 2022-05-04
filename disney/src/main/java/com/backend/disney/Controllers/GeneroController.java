package com.backend.disney.Controllers;

import com.backend.disney.Models.Genero;
import com.backend.disney.Models.Pelicula;
import com.backend.disney.Services.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/genero")
public class GeneroController {

    @Autowired
    private IGeneroService service;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Genero createGenre(@RequestBody Genero genero, @RequestParam("file") MultipartFile imagen) throws Exception {
        try {
            if (!imagen.isEmpty()) {
                Path directorioImagenes = Paths.get("src//main/resources//static/images");
                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                genero.setImagen(imagen.getOriginalFilename());
            }
            return service.createGenero(genero);
        } catch (Exception e) {
            return null;
        }
    }

}
