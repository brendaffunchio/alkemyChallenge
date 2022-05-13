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
    public Genero findByName(String nombre) throws Exception {
        Genero genero = repository.findByName(nombre);
        if(genero!= null) {
            return genero;

        }else throw new Exception(ExceptionMessages.GENRE_NULL);

    }

    @Override
    public Genero getById(Integer id) throws Exception {
        if(id==null)throw new Exception("The id is null");
        Boolean exists = repository.existsById(id);
        if(!exists)throw new Exception((ExceptionMessages.GENRE_NULL));

        return repository.getById(id);
    }

    @Override
    public Genero createGenero(Genero genero, MultipartFile imagen) throws Exception {

        Genero generoExistente = repository.findByName(genero.getNombre());

        if(generoExistente!=null) throw new Exception(ExceptionMessages.GENRE_EXISTS);
        if (genero==null)throw new Exception(ExceptionMessages.GENRE_NULL);
        if(genero.getNombre().isEmpty()) throw new Exception(ExceptionMessages.NAME_GENRE_EMPTY);
        if(genero.getNombre()==null)throw new Exception(ExceptionMessages.NAME_GENRE_NULL);
        if(imagen.isEmpty()) throw new Exception(ExceptionMessages.IMAGE_EMPTY);
        if(imagen==null)throw new Exception(ExceptionMessages.IMAGE_NULL);

        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = imagen.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        genero.setImagen(imagen.getOriginalFilename());

        return repository.save(genero);
    }
}
