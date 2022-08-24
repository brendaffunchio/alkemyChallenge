package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.GenreMapper;
import com.backend.disney.models.Genre;
import com.backend.disney.modelsDTO.GenreDTO;
import com.backend.disney.repositories.IGenreRepository;
import com.backend.disney.services.IFileService;
import com.backend.disney.services.IGenreService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GenreService implements IGenreService {


    private IGenreRepository repository;
    private GenreMapper mapper;

    private IFileService fileService;

    @Autowired
    public GenreService(IGenreRepository repository, GenreMapper mapper, IFileService fileService) {
        this.repository = repository;
        this.mapper=mapper;
        this.fileService=fileService;
    }

    @Override
    public Genre findByName(String name) throws NotFoundException {
        Genre genre = repository.findByName(name);
        if (genre == null) throw new NotFoundException(ExceptionMessages.GENRE_NOT_FOUND);

            return genre;

    }

    @Override
    public Genre getById(String id) throws NotFoundException {

        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException((ExceptionMessages.GENRE_NOT_FOUND));

        return repository.findById(id).get();
    }

    @Override
    public GenreDTO create(GenreDTO dto, MultipartFile file) throws Exception {

        Genre genreExistente = repository.findByName(dto.getName());
        if (genreExistente != null) throw new Exception(ExceptionMessages.GENRE_EXISTS);

        Genre saved= repository.save(mapper.mapGenreDTOtoEntity(dto,fileService.saveFileInFolder(file)));
        return mapper.mapEntityToDTO(saved);
    }
}
