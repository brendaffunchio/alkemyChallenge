package com.backend.disney.Controllers;

import com.backend.disney.Models.Genero;
import com.backend.disney.Services.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/genre")
public class GeneroController {

    @Autowired
    private IGeneroService service;

    @PostMapping(path = "/create")
    public String createGenre(@ModelAttribute Genero genero, @RequestParam("file") MultipartFile image, HttpServletResponse httpServletResponse) {
        try {

            httpServletResponse.setStatus(HttpStatus.OK.value());
            service.createGenero(genero,image);
            return "Genre created successfully ";

        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage();
        }
    }

}
