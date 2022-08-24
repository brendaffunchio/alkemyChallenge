package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.CharacterMapper;
import com.backend.disney.mappers.MovieMapper;
import com.backend.disney.models.Movie;
import com.backend.disney.models.Character;
import com.backend.disney.modelsDTO.CharacterDTO;
import com.backend.disney.modelsDTO.CharacterDTOComplete;
import com.backend.disney.repositories.ICharacterRepository;
import com.backend.disney.services.IFileService;
import com.backend.disney.services.IMovieService;
import com.backend.disney.services.ICharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class CharacterService implements ICharacterService {

    private ICharacterRepository repository;

    private IMovieService movieService;

    private CharacterMapper mapper;
    private MovieMapper movieMapper;

    private IFileService fileService;

    @Autowired
    public CharacterService(ICharacterRepository repository, MovieMapper movieMapper, IFileService fileService, CharacterMapper mapper, IMovieService movieService) {
        this.repository = repository;
        this.movieService = movieService;
        this.mapper = mapper;
        this.fileService = fileService;
        this.movieMapper = movieMapper;
    }

    @Override
    public CharacterDTOComplete create(CharacterDTOComplete dto, MultipartFile file) throws IOException {

        Character saved = repository.save(mapper.mapCharacterDTOCompleteToEntity(dto, fileService.saveFileInFolder(file)));

        return mapper.mapCharacterToCharacterDTOCompleto(saved, movieMapper.mapMoviesToMoviesDTO(saved.getMovies()));


    }

    @Override
    public CharacterDTOComplete update(CharacterDTOComplete dto, String id, MultipartFile file) throws NotFoundException, IOException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException(ExceptionMessages.CHARACTER_NOT_FOUND);

        Character found = repository.findById(id).get();

        found.setName(dto.getName());
        found.setAge(dto.getAge());
        found.setWeight(dto.getWeight());
        found.setHistory(dto.getHistory());
        found.setImage(fileService.saveFileInFolder(file));

        repository.save(found);

        return mapper.mapCharacterToCharacterDTOCompleto(found, movieMapper.mapMoviesToMoviesDTO(found.getMovies()));

    }

    @Override
    public Character getEntityById(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);

        if (!exists) throw new NotFoundException(ExceptionMessages.CHARACTER_NOT_FOUND);

        return repository.findById(id).get();

    }

    @Override
    public void delete(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException("Id:" + id + "->" + ExceptionMessages.CHARACTER_NOT_FOUND);

        repository.deleteById(id);

    }

    @Override
    public CharacterDTOComplete getDetails(String id) throws NotFoundException {

        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException("Id:" + id + "->" + ExceptionMessages.CHARACTER_NOT_FOUND);

        Character character = repository.findById(id).get();

        return mapper.mapCharacterToCharacterDTOCompleto(character,
                movieMapper.mapMoviesToMoviesDTO(character.getMovies()));

    }

    @Override
    public List<CharacterDTO> searchCharacters(String name, Integer age, Integer weight, String idMovie) throws NotFoundException {

        List<CharacterDTO> characterDTOS = new LinkedList<>();
        if (!name.isEmpty()) {
            characterDTOS = mapper.mapCharactersToCharactersDTO(repository.findAllByName(name));
        } else if (idMovie != null) {
            characterDTOS = getCharactersDTOByMovie(idMovie);

        } else if (age != null) {
            characterDTOS = mapper.mapCharactersToCharactersDTO(repository.findAllByAge(age));

        } else if (weight != null) {
            characterDTOS = mapper.mapCharactersToCharactersDTO(repository.findAllByWeight(weight));

        } else characterDTOS = mapper.mapCharactersToCharactersDTO(repository.findAll());

        return characterDTOS;
    }

    private List<CharacterDTO> getCharactersDTOByMovie(String idMovie) throws NotFoundException {
        Movie movie = movieService.getEntityById(idMovie);

        List<Character> movieCharacters = movie.getCharacters();


        return mapper.mapCharactersToCharactersDTO(movieCharacters);

    }
}