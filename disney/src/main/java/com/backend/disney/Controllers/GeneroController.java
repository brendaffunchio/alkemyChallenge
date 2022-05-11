package com.backend.disney.Controllers;

import com.backend.disney.Models.Genero;
import com.backend.disney.Models.Pelicula;
import com.backend.disney.Services.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@PreAuthorize("authenticated")
@RestController
@RequestMapping("/genre")
public class GeneroController {

    @Autowired
    private IGeneroService service;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public String createGenre(@RequestBody Genero genero, @RequestParam("file") MultipartFile imagen, HttpServletResponse httpServletResponse) throws Exception {
        try {
           /* if (!imagen.isEmpty()) {
                Path directorioImagenes = Paths.get("src//main/resources//static/images");
                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
                Files.write(rutaCompleta, bytesImg);
                genero.setImagen(imagen.getOriginalFilename());
            }
                 return service.createGenero(genero);
               */
            httpServletResponse.setStatus(HttpStatus.OK.value());
            service.createGenero(genero,imagen);
            return "Genre created successfully ";

        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage();
        }
    }

}
