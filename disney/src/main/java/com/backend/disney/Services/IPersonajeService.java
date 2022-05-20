package com.backend.disney.Services;

import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import com.backend.disney.ModelsDTO.PersonajeDTOCompleto;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface IPersonajeService {

    PersonajeDTOCompleto createPersonaje(Personaje personaje, MultipartFile imagen) throws Exception;

    PersonajeDTOCompleto updatePersonaje(Personaje personaje, MultipartFile imagen) throws Exception;

    Personaje getById(Integer id) throws Exception;

    void deletePersonaje(Integer idPersonaje) throws Exception;

    PersonajeDTO mapPersonajeToPersonajeDTO(Personaje personaje);

    PersonajeDTOCompleto mapPersonajeToPersonajeDTOCompleto(Personaje personaje, List<PeliculaDTO> peliculas);

    List<PersonajeDTO> mapArrayIdPersonajeToPersonajesDTO(Integer[] idPersonajes) throws Exception;

    List<PersonajeDTO> mapPersonajesToPersonajesDTO(List<Personaje> personajes);

    PersonajeDTOCompleto getDetailsPersonaje(Integer id) throws Exception;

    List<PersonajeDTO> searchPersonajes(String nombre, Integer edad, Integer peso, Integer idPelicula) throws Exception;

    List<PersonajeDTO> getPersonajesDTOByName(String name);

    List<PersonajeDTO> getPersonajesDTOByAge(Integer edad);
    List<PersonajeDTO> getPersonajesDTOByWeight(Integer peso);
    List<PersonajeDTO> getPersonajesDTOByMovie(Integer idPelicula) throws Exception;

    List<PersonajeDTO> getPersonajes();

}
