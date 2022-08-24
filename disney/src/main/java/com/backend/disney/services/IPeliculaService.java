package com.backend.disney.services;

import com.backend.disney.models.Pelicula;
import com.backend.disney.modelsDTO.PeliculaDTO;
import com.backend.disney.modelsDTO.PeliculaDTOCompleta;
import com.backend.disney.modelsDTO.PersonajeDTO;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPeliculaService {

    PeliculaDTOCompleta createPelicula(PeliculaDTOCompleta pelicula, MultipartFile file, String[] idPersonajes) throws NotFoundException, IOException;

    PeliculaDTOCompleta updatePelicula(PeliculaDTOCompleta pelicula,String id, MultipartFile file) throws NotFoundException, IOException;


    void deletePelicula(String idPelicula) throws NotFoundException;

    Pelicula getEntityById(String id) throws NotFoundException;

    PeliculaDTOCompleta getDetailsPelicula(String idPelicula) throws NotFoundException;

    List<PeliculaDTO> searchPeliculas(String nombre, String genero_id, String orden);

    void addPersonaje(String idPelicula, String idPersonaje) throws Exception;

    void removePersonaje(String idPelicula,String idPersonaje) throws Exception;

}
