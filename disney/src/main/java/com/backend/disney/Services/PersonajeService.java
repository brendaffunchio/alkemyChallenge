package com.backend.disney.Services;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import com.backend.disney.Repositories.IPersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeService implements IPersonajeService{

    @Autowired
    private IPersonajeRepository repository;

    @Override
    public Personaje createPersonaje(Personaje personaje) throws Exception {
        if(personaje!=null){
            repository.save(personaje);
        }
        else throw new Exception("Cannot create personaje");
        //falta guardar imagen
        return null;
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
    public void mapPersonajeToPersonajeDTO(Personaje personaje) {

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
        return null;
    }

    @Override
    public List<PersonajeDTO> getPersonajesDTOByName(String name) {
        return null;
    }
}
