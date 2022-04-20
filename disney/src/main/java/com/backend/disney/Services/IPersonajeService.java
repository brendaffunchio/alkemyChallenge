package com.backend.disney.Services;

import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PersonajeDTO;


import java.util.List;

public interface IPersonajeService {

    Personaje createPersonaje(Personaje personaje) throws Exception;
    Personaje updatePersonaje(Personaje personaje);
    void deletePersonaje(Integer idPersonaje) throws Exception;
    void mapPersonajeToPersonajeDTO (Personaje personaje);

    Personaje getDetailsPersonaje (Integer id) throws Exception;
    List<PersonajeDTO> getPersonajesDtoByFilter(Integer edad, Integer peso,Integer idPelicula);
    List<PersonajeDTO> getPersonajesDTOByName(String name);

}
