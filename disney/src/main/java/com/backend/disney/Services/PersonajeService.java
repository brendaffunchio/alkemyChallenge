package com.backend.disney.Services;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import com.backend.disney.Repositories.IPeliculaRepository;
import com.backend.disney.Repositories.IPersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PersonajeService implements IPersonajeService{

    @Autowired
    private IPersonajeRepository repository;

    @Autowired
    private IPeliculaRepository peliculaRepository;

    @Override
    public Personaje createPersonaje(Personaje personaje) throws Exception {
        if(personaje!=null){
            repository.save(personaje);
            return personaje;
        }
        else throw new Exception("Cannot create personaje");
        //falta guardar imagen

    }

    @Override
    public Personaje updatePersonaje(Personaje personaje) {
        return null;
    }

    @Override
    public void deletePersonaje(Integer idPersonaje) throws Exception {
       Personaje personaje = repository.getById(idPersonaje);
        if(personaje!=null){
            repository.delete(personaje);
        }else throw new Exception("Cannot delete character");
    }

    @Override
    public PersonajeDTO mapPersonajeToPersonajeDTO(Personaje personaje) {
Personaje p = repository.getById(personaje.getId());
PersonajeDTO pDTO= new PersonajeDTO(p.getImagen(),p.getNombre());
    return pDTO;
    }

    @Override
    public List<PersonajeDTO> mapPersonajesToPersonajesDTO(List<Personaje> personajes) {

        List<PersonajeDTO> personajesDTO= new LinkedList<>();

        for (Personaje personaje:personajes
             ) {
            PersonajeDTO pDTO = new PersonajeDTO(personaje.getImagen(),personaje.getNombre());
            personajesDTO.add(pDTO);
        }
        return personajesDTO;
    }

    @Override
    public Personaje getDetailsPersonaje(Integer id) throws Exception {
        Personaje personaje = repository.getById(id);
        if(personaje!=null){
            return personaje;
        }else throw new Exception("Cannot get character");
    }

    @Override
    public List<PersonajeDTO> getPersonajesDtoByFilter(Integer edad, Integer peso, Integer idPelicula) {
        //no se si hacer tod junto o el de id pelicula por separado

        if(edad!=null || peso!=null){
           List<Personaje>personajes=  repository.findAllByFilter(edad, peso);
          return mapPersonajesToPersonajesDTO(personajes);
        }else if(idPelicula!=null){
Pelicula pelicula = peliculaRepository.getById(idPelicula);
List<Personaje>personajesPelicula=pelicula.getPersonajes();

            return mapPersonajesToPersonajesDTO(personajesPelicula);

        }
        return null;
    }

    @Override
    public List<PersonajeDTO> getPersonajesDTOByName(String name) {
       List<Personaje> personajes = repository.findAllByName(name);
        return mapPersonajesToPersonajesDTO(personajes);
    }
}
