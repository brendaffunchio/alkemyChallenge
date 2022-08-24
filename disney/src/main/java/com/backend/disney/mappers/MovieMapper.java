package com.backend.disney.mappers;

import com.backend.disney.models.Genre;
import com.backend.disney.models.Movie;
import com.backend.disney.modelsDTO.MovieDTO;
import com.backend.disney.modelsDTO.MovieDTOComplete;
import com.backend.disney.modelsDTO.CharacterDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MovieMapper {


    public MovieDTO mapMovieToMovieDTO(Movie movie) {

        MovieDTO movieDTO = new MovieDTO(movie.getImage(), movie.getTitle(), movie.getCreation_date());

        return movieDTO;
    }
    public MovieDTOComplete mapMovieToMovieDTOComplete(Movie movie, List<CharacterDTO>characterDTOS){

        MovieDTOComplete dto = new MovieDTOComplete(movie.getQualification(),
                movie.getGenre().getName(),characterDTOS);
        dto.setImage(movie.getImage());
        dto.setTitle(movie.getTitle());
        dto.setCreation_date(movie.getCreation_date());
        return dto;
    }

    public List<MovieDTO> mapMoviesToMoviesDTO(List<Movie> movies) {
        List<MovieDTO> movieDTOS = new LinkedList<>();
        for (Movie movie : movies) {
            MovieDTO movieDTO = new MovieDTO(movie.getImage(), movie.getTitle(), movie.getCreation_date());
            movieDTOS.add(movieDTO);
        }
        return movieDTOS;
    }

    public Movie mapMovieDTOCompleteToEntity(MovieDTOComplete dto, Genre genre, String file){

        Movie movie = new Movie();
        movie.setQualification(dto.getQualification());
        movie.setTitle(dto.getTitle());
        movie.setGenre(genre);
        movie.setImage(file);
        movie.setCreation_date(dto.getCreation_date());

        return movie;
    }

}
