package com.backend.disney.mappers;

import com.backend.disney.models.Genre;
import com.backend.disney.modelsDTO.GenreDTO;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public Genre mapGenreDTOtoEntity(GenreDTO dto, String file){
        Genre genre = new Genre();
        genre.setName(dto.getName().toUpperCase());
        genre.setImage(file);
        return genre;
    }

    public GenreDTO mapEntityToDTO(Genre genre){
        GenreDTO dto = new GenreDTO(genre.getImage(), genre.getName());
        return dto;
    }
}
