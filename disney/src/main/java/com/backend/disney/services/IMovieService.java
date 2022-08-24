package com.backend.disney.services;

import com.backend.disney.models.Movie;
import com.backend.disney.modelsDTO.MovieDTO;
import com.backend.disney.modelsDTO.MovieDTOComplete;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMovieService {

    MovieDTOComplete create(MovieDTOComplete dto, MultipartFile file, String[] idCharacters) throws NotFoundException, IOException;

    MovieDTOComplete update(MovieDTOComplete dto, String id, MultipartFile file) throws NotFoundException, IOException;


    void delete(String id) throws NotFoundException;

    Movie getEntityById(String id) throws NotFoundException;

    MovieDTOComplete getDetailsMovie(String idMovie) throws NotFoundException;

    List<MovieDTO> searchMovies(String name, String idGenre, String order);

    void addCharacter(String idMovie, String idCharacter) throws Exception;

    void removeCharacter(String idMovie, String idCharacter) throws Exception;

}
