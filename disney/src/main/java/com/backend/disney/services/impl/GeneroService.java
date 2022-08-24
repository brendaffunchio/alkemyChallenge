package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.GenreMapper;
import com.backend.disney.models.Genero;
import com.backend.disney.modelsDTO.GeneroDTO;
import com.backend.disney.repositories.IGeneroRepository;
import com.backend.disney.services.IGeneroService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GeneroService implements IGeneroService {


    private IGeneroRepository repository;
    private GenreMapper mapper;

    @Autowired
    public GeneroService(IGeneroRepository repository,GenreMapper mapper) {
        this.repository = repository;
        this.mapper=mapper;
    }

    @Override
    public Genero findByName(String nombre) throws NotFoundException {
        Genero genero = repository.findByName(nombre);
        if (genero == null) throw new NotFoundException(ExceptionMessages.GENRE_NULL);

            return genero;

    }

    @Override
    public Genero getById(String id) throws NotFoundException {

        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException((ExceptionMessages.GENRE_NULL));

        return repository.findById(id).get();
    }

    @Override
    public GeneroDTO createGenero(GeneroDTO genero, MultipartFile imagen) throws Exception {

        Genero generoExistente = repository.findByName(genero.getNombre());
        if (generoExistente != null) throw new Exception(ExceptionMessages.GENRE_EXISTS);

            Path directorioImagenes = Paths.get("src//main/resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            byte[] bytesImg = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);

            Genero saved= repository.save(mapper.mapGenreDTOtoEntity(genero,imagen.getOriginalFilename()));
        return mapper.mapEntityToDTO(saved);
    }
}
