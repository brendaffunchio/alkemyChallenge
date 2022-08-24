package com.backend.disney.mappers;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.models.Genero;
import com.backend.disney.models.Pelicula;
import com.backend.disney.models.Personaje;
import com.backend.disney.modelsDTO.PeliculaDTO;
import com.backend.disney.modelsDTO.PeliculaDTOCompleta;
import com.backend.disney.modelsDTO.PersonajeDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MovieMapper {


    public PeliculaDTO mapMovieToPeliculaDTO(Pelicula pelicula) {

        PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(), pelicula.getTitulo(), pelicula.getFecha_creacion());

        return peliculaDTO;
    }
    public PeliculaDTOCompleta mapMovieToPeliculaDTOCompleta(Pelicula pelicula, List<PersonajeDTO>personajes){

        PeliculaDTOCompleta dto = new PeliculaDTOCompleta(pelicula.getCalificacion(),
                pelicula.getGenero().getNombre(),personajes);
        dto.setImagen(pelicula.getImagen());
        dto.setTitulo(pelicula.getTitulo());
        dto.setFecha_creacion(pelicula.getFecha_creacion());
        return dto;
    }

    public List<PeliculaDTO> mapPeliculasToPeliculasDTO(List<Pelicula> peliculas) {
        List<PeliculaDTO> peliculasDto = new LinkedList<>();
        for (Pelicula pelicula : peliculas) {
            PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(), pelicula.getTitulo(), pelicula.getFecha_creacion());
            peliculasDto.add(peliculaDTO);
        }
        return peliculasDto;
    }

    public Pelicula mapPeliculaDTOCompletaToEntity(PeliculaDTOCompleta dto, Genero genero, String file){

        Pelicula pelicula = new Pelicula();
        pelicula.setCalificacion(dto.getCalificacion());
        pelicula.setTitulo(dto.getTitulo());
        pelicula.setGenero(genero);
        pelicula.setImagen(file);
        pelicula.setFecha_creacion(dto.getFecha_creacion());

        return pelicula;
    }
}
