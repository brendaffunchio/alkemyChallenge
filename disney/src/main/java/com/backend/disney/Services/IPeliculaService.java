package com.backend.disney.Services;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.ModelsDTO.PeliculaDTOCompleta;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IPeliculaService {

    PeliculaDTOCompleta createPelicula(Pelicula pelicula, MultipartFile imagen, Integer genero, Integer[] idPersonajes) throws Exception;

    PeliculaDTOCompleta updatePelicula(Pelicula pelicula, MultipartFile imagen, Integer genero) throws Exception;

    Pelicula getById(Integer id) throws Exception;

    void deletePelicula(Integer idPelicula) throws Exception;

    PeliculaDTO mapPeliculaToPeliculaDTO(Pelicula pelicula);

    void saveCharactersInMovie (Integer [] idPersonajes,Pelicula pelicula) throws Exception;

    PeliculaDTOCompleta mapPeliculaToPeliculaDTOCompleta(Pelicula pelicula, List<PersonajeDTO> personajes) throws Exception;

    List<PeliculaDTO> mapPeliculasToPeliculasDTO(List<Pelicula> peliculas);

    PeliculaDTOCompleta getDetailsPelicula(Integer idPelicula) throws Exception;

    List<PeliculaDTO> searchPeliculas(String nombre, Integer idGenero, String orden) throws Exception;

    List<PeliculaDTO> getPeliculasDTOByName(String nombre);

    List<PeliculaDTO> getPeliculasDTOByFilterGenero(Integer idGenero);

    List<PeliculaDTO> getPeliculas();

    List<PeliculaDTO> orderResultsPeliculasDTO(String orden, List<PeliculaDTO> peliculasDTO);

    void addPersonaje(Integer idPelicula, Integer idPersonaje) throws Exception;

    void removePersonaje(Integer idPelicula, Integer idPersonaje) throws Exception;

}
