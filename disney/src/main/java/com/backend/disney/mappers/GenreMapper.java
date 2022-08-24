package com.backend.disney.mappers;

import com.backend.disney.models.Genero;
import com.backend.disney.modelsDTO.GeneroDTO;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public Genero mapGenreDTOtoEntity(GeneroDTO dto, String file){
        Genero genero = new Genero();
        genero.setNombre(dto.getNombre());
        genero.setImagen(file);
        return genero;
    }

    public GeneroDTO mapEntityToDTO(Genero genero){
        GeneroDTO dto = new GeneroDTO(genero.getImagen(), genero.getNombre());
        return dto;
    }
}
