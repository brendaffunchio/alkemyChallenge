package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.models.Pelicula;
import com.backend.disney.models.Personaje;
import com.backend.disney.modelsDTO.PeliculaDTO;
import com.backend.disney.modelsDTO.PersonajeDTO;
import com.backend.disney.modelsDTO.PersonajeDTOCompleta;
import com.backend.disney.repositories.IPeliculaRepository;
import com.backend.disney.repositories.IPersonajeRepository;
import com.backend.disney.services.IPeliculaService;
import com.backend.disney.services.IPersonajeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Service
public class PersonajeService implements IPersonajeService {

    private IPersonajeRepository repository;

    private IPeliculaService peliculaService;

    private IPeliculaRepository peliculaRepository;
    @Autowired
    public PersonajeService(IPersonajeRepository repository, IPeliculaService peliculaService, IPeliculaRepository peliculaRepository) {
        this.repository = repository;
        this.peliculaService = peliculaService;
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    public PersonajeDTOCompleta createPersonaje(Personaje personaje, MultipartFile imagen) throws Exception {
        if (personaje == null) throw new Exception(ExceptionMessages.CHARACTER_NULL);
        if (personaje.getNombre().isEmpty()) throw new Exception(ExceptionMessages.NAME_CHARACTER_EMPTY);
        if (personaje.getNombre() == null) throw new Exception(ExceptionMessages.NAME_CHARACTER_NULL);
        if (imagen.isEmpty()) throw new Exception(ExceptionMessages.IMAGE_EMPTY);
        if (imagen == null) throw new Exception(ExceptionMessages.IMAGE_NULL);
        if (!personaje.getHistoria().isEmpty()) {
            if (personaje.getHistoria().length() > 255)
                throw new Exception(ExceptionMessages.STORY_CHARACTER_LENGTH);
        }
        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = imagen.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        personaje.setImagen(imagen.getOriginalFilename());

        personaje.setBorrado(false);
        repository.save(personaje);

        PersonajeDTOCompleta personajeDTOCompleta = mapPersonajeToPersonajeDTOCompleto(personaje,
                peliculaService.mapPeliculasToPeliculasDTO(personaje.getPeliculas()));

        return personajeDTOCompleta;


    }

    @Override
    public PersonajeDTOCompleta updatePersonaje(Personaje personaje, MultipartFile imagen) throws Exception {
        Boolean exists = repository.existsById(personaje.getId());
        if (!exists) throw new Exception(ExceptionMessages.CHARACTER_NULL);

        Personaje personajeExistente = repository.getById(personaje.getId());

        if (!personaje.getNombre().isEmpty()) personajeExistente.setNombre(personaje.getNombre());
        if (personaje.getEdad() != null) personajeExistente.setEdad(personaje.getEdad());
        if (personaje.getPeso() != null) personajeExistente.setPeso(personaje.getPeso());
        if (!personaje.getHistoria().isEmpty()) {
            if (personaje.getHistoria().length() > 255)
                throw new Exception(ExceptionMessages.STORY_CHARACTER_LENGTH);
            personajeExistente.setHistoria(personaje.getHistoria());
        }

        if (!imagen.isEmpty()) {

            Path directorioImagenes = Paths.get("src//main/resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            byte[] bytesImg = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);
            personajeExistente.setImagen(imagen.getOriginalFilename());
        }

        personajeExistente.setBorrado(false);
        repository.save(personajeExistente);

        PersonajeDTOCompleta personajeDTOCompleta = mapPersonajeToPersonajeDTOCompleto(personajeExistente,
                peliculaService.mapPeliculasToPeliculasDTO(personajeExistente.getPeliculas()));

        return personajeDTOCompleta;

    }

    @Override
    public Personaje getById(Integer id) throws NotFoundException {
        Boolean personajeExists = repository.existsById(id);

        if (!personajeExists) throw new NotFoundException(ExceptionMessages.CHARACTER_NULL);

        Personaje personaje = repository.getById(id);
     //   if (personaje.getBorrado() == true) throw new Exception(ExceptionMessages.CHARACTER_NOT_FOUND);

        return personaje;
    }

    @Override
    public void deletePersonaje(Integer idPersonaje) throws Exception {
        if (idPersonaje == null) throw new Exception(ExceptionMessages.ID_CHARACTER_DELETE_NULL);
        Boolean exists = repository.existsById(idPersonaje);
        if (!exists) throw new Exception("Id:" + idPersonaje + "->" + ExceptionMessages.CHARACTER_NOT_FOUND);
        Personaje p = repository.getById(idPersonaje);
        if(p.getBorrado()==true)throw new Exception(ExceptionMessages.CHARACTER_DELETED);
        p.setBorrado(true);

        repository.save(p);

    }

    @Override
    public PersonajeDTO mapPersonajeToPersonajeDTO(Personaje personaje) {
        Personaje p = repository.getById(personaje.getId());
        PersonajeDTO pDTO = new PersonajeDTO(p.getImagen(), p.getNombre());
        return pDTO;
    }

    @Override
    public PersonajeDTOCompleta mapPersonajeToPersonajeDTOCompleto(Personaje personaje, List<PeliculaDTO> peliculas) {
        PersonajeDTOCompleta personajeDTOCompleta = new PersonajeDTOCompleta(personaje.getImagen(),
                personaje.getNombre(), personaje.getEdad(), personaje.getPeso(), personaje.getHistoria(), peliculas);

        return personajeDTOCompleta;

    }

    @Override
    public List<PersonajeDTO> mapArrayIdPersonajeToPersonajesDTO(Integer[] idPersonajes) throws Exception {
        List<Personaje> personajes = new LinkedList<>();
        for (Integer id : idPersonajes) {
            Boolean exists = repository.existsById(id);
            if (!exists) throw new Exception(ExceptionMessages.CHARACTER_NULL + " " + "ID:" + id);
            Personaje p = getById(id);
            personajes.add(p);
        }
        List<PersonajeDTO> personajesDTO = mapPersonajesToPersonajesDTO(personajes);
        return personajesDTO;
    }

    @Override
    public List<PersonajeDTO> mapPersonajesToPersonajesDTO(List<Personaje> personajes) {

        List<PersonajeDTO> personajesDTO = new LinkedList<>();

        for (Personaje personaje : personajes
        ) {
            PersonajeDTO pDTO = new PersonajeDTO(personaje.getImagen(), personaje.getNombre());
            personajesDTO.add(pDTO);
        }
        return personajesDTO;
    }

    @Override
    public PersonajeDTOCompleta getDetailsPersonaje(Integer id) throws Exception {
        if (id == null) throw new Exception(ExceptionMessages.ID_CHARACTER_GET_DETAILS_NULL);
        Boolean exists = repository.existsById(id);
        if (!exists) throw new Exception("Id:" + id + "->" + ExceptionMessages.CHARACTER_NOT_FOUND);

        Personaje personaje = repository.getById(id);
        if (personaje.getBorrado() == true) throw new Exception(ExceptionMessages.CHARACTER_NOT_FOUND);

        PersonajeDTOCompleta personajeDTOCompleta = mapPersonajeToPersonajeDTOCompleto(personaje,
                peliculaService.mapPeliculasToPeliculasDTO(personaje.getPeliculas()));

        return personajeDTOCompleta;

    }

    @Override
    public List<PersonajeDTO> searchPersonajes(String nombre, Integer edad, Integer peso, Integer idPelicula) throws Exception {
        if (nombre == null) throw new Exception(ExceptionMessages.NAME_CHARACTER_NULL);

        List<PersonajeDTO> personajes = new LinkedList<>();
        if (!nombre.isEmpty()) {
            personajes = getPersonajesDTOByName(nombre);
        } else if (idPelicula != null) {
            personajes = getPersonajesDTOByMovie(idPelicula);

        } else if (edad != null) {
          personajes= getPersonajesDTOByAge(edad);

        } else if (peso != null) {
           personajes = getPersonajesDTOByWeight(peso);

        }else personajes = getPersonajes();

        return personajes;
    }

        @Override
        public List<PersonajeDTO> getPersonajesDTOByName (String name){
            List<Personaje> personajes = repository.findAllByName(name);
            return mapPersonajesToPersonajesDTO(personajes);
        }

        @Override
        public List<PersonajeDTO> getPersonajesDTOByAge (Integer edad){
            List<Personaje> personajes = repository.findAllByAge(edad);
            return mapPersonajesToPersonajesDTO(personajes);
        }
        @Override
        public List<PersonajeDTO> getPersonajesDTOByWeight (Integer peso){
            List<Personaje> personajes = repository.findAllByWeight(peso);
            return mapPersonajesToPersonajesDTO(personajes);
        }
        @Override
        public List<PersonajeDTO> getPersonajesDTOByMovie (Integer idPelicula) throws Exception {
        Boolean exists= peliculaRepository.existsById(idPelicula);
        if(!exists) throw new Exception(ExceptionMessages.MOVIE_NULL);
          Pelicula pelicula = peliculaRepository.getById(idPelicula);

                List<Personaje> personajesPelicula = pelicula.getPersonajes();


                return mapPersonajesToPersonajesDTO(personajesPelicula);

        }

        @Override
        public List<PersonajeDTO> getPersonajes () {
            List<Personaje> personajes = repository.findAll();
            return mapPersonajesToPersonajesDTO(personajes);
        }
    }
