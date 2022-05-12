package com.backend.disney.Services;

import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface IPersonajeService {

    Personaje createPersonaje(Personaje personaje, MultipartFile imagen) throws Exception;
    Personaje updatePersonaje(Personaje personaje,MultipartFile imagen)throws Exception ;
    void deletePersonaje(Integer idPersonaje) throws Exception;
    PersonajeDTO mapPersonajeToPersonajeDTO (Personaje personaje);
    List<PersonajeDTO> mapPersonajesToPersonajesDTO( List<Personaje> personajes);
    Personaje getDetailsPersonaje (Integer id) throws Exception;
    List<PersonajeDTO> searchPersonajes(String nombre,Integer edad, Integer peso,Integer idPelicula);
    List<PersonajeDTO> getPersonajesDTOByName(String name);
    List<PersonajeDTO> getPersonajesDTOByAgeOrweight(Integer edad, Integer peso);
    List<PersonajeDTO> getPersonajesDTOByMovie(Integer idPelicula);

    List<PersonajeDTO> getPersonajes();

}
