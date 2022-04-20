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


    }

    @Override
    public Personaje updatePersonaje(Personaje personaje) throws Exception  {
Personaje personajeExistente = repository.getById(personaje.getId());
if(personajeExistente!=null){
    personajeExistente.setImagen(personaje.getImagen());
    personajeExistente.setEdad(personaje.getEdad());
    personajeExistente.setHistoria(personaje.getHistoria());
    personajeExistente.setPeso(personaje.getPeso());
    personajeExistente.setNombre(personaje.getNombre());
    repository.save(personajeExistente);
    return personajeExistente;
}else throw new Exception("Cannot update character");
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
    public List<PersonajeDTO> searchPersonajes(String nombre,Integer edad, Integer peso, Integer idPelicula) {

        if(nombre!=null) {
            return getPersonajesDTOByName(nombre);
        }else if(edad!=null || peso!=null){
          return getPersonajesDTOByAgeOrweight(edad,peso);
        }else if(idPelicula!=null){
            return getPersonajesDTOByMovie(idPelicula);

        }
        return getPersonajes();
    }

    @Override
    public List<PersonajeDTO> getPersonajesDTOByName(String name) {
       List<Personaje> personajes = repository.findAllByName(name);
        return mapPersonajesToPersonajesDTO(personajes);
    }

    @Override
    public List<PersonajeDTO> getPersonajesDTOByAgeOrweight(Integer edad, Integer peso) {
        List<Personaje>personajes=  repository.findAllByFilter(edad, peso);
        return mapPersonajesToPersonajesDTO(personajes);
    }

    @Override
    public List<PersonajeDTO> getPersonajesDTOByMovie(Integer idPelicula) {
        Pelicula pelicula = peliculaRepository.getById(idPelicula);
        if(pelicula!=null) {
            List<Personaje> personajesPelicula = pelicula.getPersonajes();

            return mapPersonajesToPersonajesDTO(personajesPelicula);
        }
        return null;
    }

    @Override
    public List<PersonajeDTO> getPersonajes() {
        List<Personaje> personajes = repository.findAll();
        return mapPersonajesToPersonajesDTO(personajes);
    }
}
