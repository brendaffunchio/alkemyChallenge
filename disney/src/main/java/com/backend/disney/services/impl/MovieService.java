package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.CharacterMapper;
import com.backend.disney.mappers.MovieMapper;
import com.backend.disney.models.Genre;
import com.backend.disney.models.Movie;
import com.backend.disney.models.Character;
import com.backend.disney.modelsDTO.MovieDTO;
import com.backend.disney.modelsDTO.MovieDTOComplete;
import com.backend.disney.repositories.IMovieRepository;
import com.backend.disney.repositories.ICharacterRepository;
import com.backend.disney.services.IFileService;
import com.backend.disney.services.IGenreService;
import com.backend.disney.services.IMovieService;
import com.backend.disney.services.ICharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MovieService implements IMovieService {

    private IMovieRepository repository;
    private ICharacterRepository characterRepository;
    private ICharacterService characterService;
    private IGenreService genreService;
    private MovieMapper mapper;
    private CharacterMapper characterMapper;
    private IFileService fileService;


    @Autowired
    public MovieService(IMovieRepository repository, IFileService fileService, CharacterMapper characterMapper, MovieMapper mapper, ICharacterRepository characterRepository, ICharacterService characterService, IGenreService genreService) {
        this.repository = repository;
        this.characterRepository = characterRepository;
        this.characterService = characterService;
        this.genreService = genreService;
        this.mapper = mapper;
        this.characterMapper = characterMapper;
        this.fileService = fileService;
    }

    @Override
    public MovieDTOComplete create(MovieDTOComplete dto, MultipartFile file, String[] idCharacters) throws NotFoundException, IOException {

        Genre found = genreService.findByName(dto.getGenre());

        Movie toSave = mapper.mapMovieDTOCompleteToEntity(dto, found, fileService.saveFileInFolder(file));

        if (Arrays.stream(idCharacters).count() != 0) {
            saveCharactersInMovie(idCharacters, toSave);
        }

        Movie saved = repository.save(toSave);
        return mapper.mapMovieToMovieDTOComplete(saved, characterMapper.mapCharactersToCharactersDTO(saved.getCharacters()));
    }

    @Override
    public MovieDTOComplete update(MovieDTOComplete dto, String id, MultipartFile file) throws NotFoundException, IOException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException(ExceptionMessages.MOVIE_NOT_FOUND);
        Movie movieFound = repository.findById(id).get();

        movieFound.setQualification(dto.getQualification());
        movieFound.setTitle(dto.getTitle());
        movieFound.setCreation_date(dto.getCreation_date());

        Genre genreFound = genreService.findByName(dto.getGenre());
        movieFound.setGenre(genreFound);

        movieFound.setImage(fileService.saveFileInFolder(file));

        repository.save(movieFound);


        return mapper.mapMovieToMovieDTOComplete(movieFound,
                characterMapper.mapCharactersToCharactersDTO(movieFound.getCharacters()));

    }

    @Override
    public void delete(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException("Id:" + id + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        repository.deleteById(id);
    }

    @Override
    public Movie getEntityById(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException("Id:" + id + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        return repository.findById(id).get();

    }

    @Override
    public MovieDTOComplete getDetailsMovie(String idMovie) throws NotFoundException {
        Boolean exists = repository.existsById(idMovie);
        if (!exists) throw new NotFoundException("Id:" + idMovie + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);
        Movie found = repository.findById(idMovie).get();

        return mapper.mapMovieToMovieDTOComplete(found,characterMapper.mapCharactersToCharactersDTO(found.getCharacters()));

    }

    @Override
    public List<MovieDTO> searchMovies(String name, String idGenre, String order) {
        List<MovieDTO> movieDTOS = new ArrayList<>();

        if (!name.isEmpty()) {
            movieDTOS = mapper.mapMoviesToMoviesDTO(repository.findAllByName(name));

        } else if (!idGenre.isEmpty()) {

            movieDTOS = mapper.mapMoviesToMoviesDTO(repository.findAllByGenre(idGenre));

        } else movieDTOS = mapper.mapMoviesToMoviesDTO(repository.findAll());

        if (!order.isEmpty()) {

            movieDTOS = orderResultsMoviesDTO(order, movieDTOS);
        }
        return movieDTOS;
    }

    @Override
    public void addCharacter(String idMovie, String idCharacter) throws Exception {
        Movie movie = getEntityById(idMovie);
        Character character = characterService.getEntityById(idCharacter);

        if (movie.getCharacters().contains(character))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_CONTAINS);

        movie.getCharacters().add(character);
        repository.save(movie);
        characterRepository.save(character);
    }

    @Override
    public void removeCharacter(String idMovie, String idCharacter) throws Exception {
        Movie movie = getEntityById(idMovie);
        Character character = characterService.getEntityById(idCharacter);

        if (!movie.getCharacters().contains(character))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_NOT_CONTAINS);

        movie.getCharacters().remove(character);
        repository.save(movie);
        characterRepository.save(character);
    }

    private void saveCharactersInMovie(String[] idCharacters, Movie movie) throws NotFoundException {
        for (String id : idCharacters) {
            Boolean exists = characterRepository.existsById(id);
            if (!exists) throw new NotFoundException(ExceptionMessages.CHARACTER_NOT_FOUND + " " + "ID:" + id);
            Character p = characterService.getEntityById(id);
            movie.getCharacters().add(p);
            characterRepository.save(p);
            repository.save(movie);
        }
    }

    private List<MovieDTO> orderResultsMoviesDTO(String order, List<MovieDTO> movieDTOS) {
        if (order.toUpperCase().equals("ASC")) {
            movieDTOS.sort(Comparator.comparing(MovieDTO::getCreation_date));
        } else {
            movieDTOS.sort(Comparator.comparing(MovieDTO::getCreation_date).reversed());
        }
        return movieDTOS;
    }


}
