package com.backend.disney.Services.impl;

import com.backend.disney.Exception.ExceptionMessages;
import com.backend.disney.Models.Genero;
import com.backend.disney.Repositories.IGeneroRepository;
import com.backend.disney.Services.IGeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GeneroService implements IGeneroService {

 @Autowired
 private IGeneroRepository repository;

    @Override
    public Genero createGenero(Genero genero) throws Exception {
 if(genero != null) {
     repository.save(genero);
     return genero;
 }else throw new Exception("Cannot create genre");
    }

    @Override
    public Genero createGenero(Genero genero, MultipartFile imagen) throws Exception {

        if (genero==null)throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_GENRE);
        if(genero.getNombre().isEmpty()) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_NAME_GENRE);
        if(imagen.isEmpty()) throw new Exception(ExceptionMessages.MSG_BAD_REQUEST_IMAGE);

        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = imagen.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        genero.setImagen(imagen.getOriginalFilename());

        return repository.save(genero);
    }
}
