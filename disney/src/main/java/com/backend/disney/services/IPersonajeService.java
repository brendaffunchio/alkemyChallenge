package com.backend.disney.services;

import com.backend.disney.models.Personaje;
import com.backend.disney.modelsDTO.PeliculaDTO;
import com.backend.disney.modelsDTO.PersonajeDTO;
import com.backend.disney.modelsDTO.PersonajeDTOCompleta;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface IPersonajeService {

    PersonajeDTOCompleta createPersonaje(Personaje personaje, MultipartFile imagen) throws Exception;

    PersonajeDTOCompleta updatePersonaje(Personaje personaje, MultipartFile imagen) throws Exception;

    Personaje getById(String id) throws NotFoundException;

    void deletePersonaje(String idPersonaje) throws Exception;

    PersonajeDTO mapPersonajeToPersonajeDTO(Personaje personaje);

    PersonajeDTOCompleta mapPersonajeToPersonajeDTOCompleto(Personaje personaje, List<PeliculaDTO> peliculas);

    List<PersonajeDTO> mapArrayIdPersonajeToPersonajesDTO(String[] idPersonajes) throws Exception;

    List<PersonajeDTO> mapPersonajesToPersonajesDTO(List<Personaje> personajes);

    PersonajeDTOCompleta getDetailsPersonaje(String id) throws Exception;

    List<PersonajeDTO> searchPersonajes(String nombre, Integer edad, Integer peso,String idPelicula) throws Exception;

    List<PersonajeDTO> getPersonajesDTOByName(String name);

    List<PersonajeDTO> getPersonajesDTOByAge(Integer edad);
    List<PersonajeDTO> getPersonajesDTOByWeight(Integer peso);
    List<PersonajeDTO> getPersonajesDTOByMovie(String idPelicula) throws Exception;

    List<PersonajeDTO> getPersonajes();

}
