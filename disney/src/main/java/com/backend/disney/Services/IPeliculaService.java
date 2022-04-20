package com.backend.disney.Services;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.ModelsDTO.PeliculaDTO;

import java.util.List;

public interface IPeliculaService {

Pelicula createPelicula(Pelicula pelicula) throws Exception;

    Pelicula updatePelicula(Pelicula pelicula) throws Exception;

   void deletePelicula(Integer idPelicula) throws Exception;
   void mapPeliculaToPeliculaDTO(Pelicula pelicula);
   List<PeliculaDTO> mapPeliculasToPeliculasDTO(List<Pelicula>peliculas);
   Pelicula getDetailsPelicula(Integer idPelicula) throws Exception;

   List<PeliculaDTO> getPeliculasDTOByName(String nombre);
    List<PeliculaDTO>  getPeliculasDTOByFilterGenero(Integer idGenero);
    List<PeliculaDTO> getPeliculas();
   List <PeliculaDTO> orderResultsPeliculasDTO(String orden,List<PeliculaDTO>peliculasDTO);

   void addPersonaje (Integer idPelicula, Integer idPersonaje) throws Exception;
   void removePersonaje(Integer idPelicula, Integer idPersonaje) throws Exception;

}
