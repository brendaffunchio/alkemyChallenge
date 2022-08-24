package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.MovieMapper;
import com.backend.disney.models.Genero;
import com.backend.disney.models.Pelicula;
import com.backend.disney.models.Personaje;
import com.backend.disney.modelsDTO.PeliculaDTO;
import com.backend.disney.modelsDTO.PeliculaDTOCompleta;
import com.backend.disney.repositories.IPeliculaRepository;
import com.backend.disney.repositories.IPersonajeRepository;
import com.backend.disney.services.IGeneroService;
import com.backend.disney.services.IPeliculaService;
import com.backend.disney.services.IPersonajeService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PeliculaService implements IPeliculaService {

    private IPeliculaRepository repository;
    private IPersonajeRepository personajeRepository;
    private IPersonajeService personajeService;
    private IGeneroService generoService;
    private MovieMapper mapper;

    @Autowired
    public PeliculaService(IPeliculaRepository repository, MovieMapper mapper, IPersonajeRepository personajeRepository, IPersonajeService personajeService, IGeneroService generoService) {
        this.repository = repository;
        this.personajeRepository = personajeRepository;
        this.personajeService = personajeService;
        this.generoService = generoService;
        this.mapper = mapper;
    }

    @Override
    public PeliculaDTOCompleta createPelicula(PeliculaDTOCompleta pelicula, MultipartFile file, String[] idPersonajes) throws NotFoundException, IOException {
        Genero generoExistente = generoService.findByName(pelicula.getGenero());

        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = file.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        pelicula.setImagen(file.getOriginalFilename());

        Pelicula movie = mapper.mapPeliculaDTOCompletaToEntity(pelicula, generoExistente, file.getOriginalFilename());

        if (Arrays.stream(idPersonajes).count() != 0) {
            saveCharactersInMovie(idPersonajes, movie);
        }

        Pelicula saved = repository.save(movie);
        return mapper.mapMovieToPeliculaDTOCompleta(saved, personajeService.mapPersonajesToPersonajesDTO(saved.getPersonajes()));
    }

    @Override
    public PeliculaDTOCompleta updatePelicula(PeliculaDTOCompleta pelicula, String id, MultipartFile file) throws NotFoundException, IOException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException(ExceptionMessages.MOVIE_NOT_FOUND);

        Pelicula peliculaExistente = repository.findById(id).get();

        peliculaExistente.setCalificacion(pelicula.getCalificacion());
        peliculaExistente.setTitulo(pelicula.getTitulo());
        peliculaExistente.setFecha_creacion(pelicula.getFecha_creacion());

        Genero generoExistente = generoService.findByName(pelicula.getGenero());
        peliculaExistente.setGenero(generoExistente);

        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = file.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        peliculaExistente.setImagen(file.getOriginalFilename());

        repository.save(peliculaExistente);


        return mapper.mapMovieToPeliculaDTOCompleta(peliculaExistente,
                personajeService.mapPersonajesToPersonajesDTO(peliculaExistente.getPersonajes()));

    }

    @Override
    public void deletePelicula(String idPelicula) throws NotFoundException {
        Boolean pelicula = repository.existsById(idPelicula);
        if (!pelicula) throw new NotFoundException("Id:" + idPelicula + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        repository.deleteById(idPelicula);
    }

    @Override
    public Pelicula getEntityById(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);
        if (!exists)throw new NotFoundException("Id:" + id + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        return repository.findById(id).get();

    }

    @Override
    public PeliculaDTOCompleta getDetailsPelicula(String idPelicula) throws NotFoundException {
        Boolean exists = repository.existsById(idPelicula);
        if (!exists)throw new NotFoundException("Id:" + idPelicula + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);
      Pelicula found= repository.findById(idPelicula).get();
        return mapper.mapMovieToPeliculaDTOCompleta(found,
                personajeService.mapPersonajesToPersonajesDTO(found.getPersonajes()));

    }

    @Override
    public List<PeliculaDTO> searchPeliculas(String nombre, String genero_id, String orden) {
    List<PeliculaDTO> peliculas = new ArrayList<>();

        if (!nombre.isEmpty()) {
            peliculas = getPeliculasDTOByName(nombre);

        } else if (!genero_id.isEmpty()) {

            peliculas = getPeliculasDTOByFilterGenero(genero_id);

        } else peliculas = getPeliculas();

        if (!orden.isEmpty()) {

            peliculas = orderResultsPeliculasDTO(orden, peliculas);
        }
        return peliculas;
    }

    @Override
    public void addPersonaje(String idPelicula, String idPersonaje) throws Exception {
        Pelicula pelicula = getEntityById(idPelicula);
        Personaje personaje = personajeService.getById(idPersonaje);

        if (pelicula.getPersonajes().contains(personaje))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_CONTAINS);

        pelicula.getPersonajes().add(personaje);
        repository.save(pelicula);
        personajeRepository.save(personaje);
    }

    @Override
    public void removePersonaje(String idPelicula, String idPersonaje) throws Exception {
        Pelicula pelicula = getEntityById(idPelicula);
        Personaje personaje = personajeService.getById(idPersonaje);

        if (!pelicula.getPersonajes().contains(personaje))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_NOT_CONTAINS);

        pelicula.getPersonajes().remove(personaje);
        repository.save(pelicula);
        personajeRepository.save(personaje);
    }

    private void saveCharactersInMovie(String[] idPersonajes, Pelicula pelicula) throws NotFoundException {
        List<Personaje> personajes = new LinkedList<>();
        for (String id : idPersonajes) {
            Boolean exists = personajeRepository.existsById(id);
            if (!exists) throw new NotFoundException(ExceptionMessages.CHARACTER_NULL + " " + "ID:" + id);
            Personaje p = personajeService.getById(id);
            pelicula.getPersonajes().add(p);
            personajeRepository.save(p);
            repository.save(pelicula);
        }
    }

    private List<PeliculaDTO> orderResultsPeliculasDTO(String orden, List<PeliculaDTO> peliculasDTO) {
        if (orden.equals("ASC")) {
            peliculasDTO.sort(Comparator.comparing(PeliculaDTO::getFecha_creacion));
        } else {
            peliculasDTO.sort(Comparator.comparing(PeliculaDTO::getFecha_creacion).reversed());
        }
        return peliculasDTO;
    }

    private List<PeliculaDTO> getPeliculasDTOByName(String nombre) {
        List<Pelicula> peliculas = repository.findAllByName(nombre);
        return mapper.mapPeliculasToPeliculasDTO(peliculas);
    }

    private List<PeliculaDTO> getPeliculasDTOByFilterGenero(String idGenero) {
        List<Pelicula> peliculas = repository.findAllByGenre(idGenero);

        return mapper.mapPeliculasToPeliculasDTO(peliculas);
    }


    private List<PeliculaDTO> getPeliculas() {
        List<Pelicula> peliculas = repository.findAll();

        return mapper.mapPeliculasToPeliculasDTO(peliculas);
    }
/*
    @Override
    public PeliculaDTOCompleta createPelicula(PeliculaDTOCompleta pelicula, MultipartFile file, Integer[] idPersonajes) throws Exception {

        Genero generoExistente = generoService.findByName(pelicula.getGenero());
        pelicula.setGenero(generoExistente);


        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = file.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" +file.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        pelicula.setImagen(file.getOriginalFilename());

        if(Arrays.stream(idPersonajes).count()!=0) {
            saveCharactersInMovie(idPersonajes, pelicula);
        }
        repository.save(pelicula);


      PeliculaDTOResponse pDTOcompleta= mapPeliculaToPeliculaDTOCompleta(pelicula,
              personajeService.mapPersonajesToPersonajesDTO(pelicula.getPersonajes()));

        return pDTOcompleta;


    }

    @Override
    public PeliculaDTOCompleta updatePelicula(PeliculaDTOCompleta pelicula, String id, MultipartFile file) throws NotFoundException {

        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException(ExceptionMessages.MOVIE_NOT_FOUND);

        Pelicula peliculaExistente = repository.getById(id);

        peliculaExistente.setCalificacion(pelicula.getCalificacion());
        peliculaExistente.setTitulo(pelicula.getTitulo());
        peliculaExistente.setFecha_creacion(pelicula.getFecha_creacion());

        if (pelicula.getGenero() != null) {
            Genero generoExistente = generoService.getById(pelicula.getGenero());
            peliculaExistente.setGenero(generoExistente);
            //hacer el genero not found
        }
        if (!file.isEmpty()) {

            Path directorioImagenes = Paths.get("src//main/resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            byte[] bytesImg = file.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);
            peliculaExistente.setImagen(file.getOriginalFilename());
        }
        repository.save(peliculaExistente);

        PeliculaDTOCompleta pDTOcompleta= mapPeliculaToPeliculaDTOCompleta(peliculaExistente,
                personajeService.mapPersonajesToPersonajesDTO(peliculaExistente.getPersonajes()));

        return pDTOcompleta;

    }

    @Override
    public Pelicula getById(Integer id) throws Exception {
        Boolean peliculaExists = repository.existsById(id);

        if (!peliculaExists) throw new Exception(ExceptionMessages.MOVIE_NULL);

        Pelicula pelicula = repository.getById(id);

        return pelicula;

    }

    @Override
    public void deletePelicula(Integer idPelicula) throws Exception {
        if (idPelicula == null) throw new Exception(ExceptionMessages.ID_MOVIE_DELETE_NULL);
        Boolean pelicula = repository.existsById(idPelicula);
        if (!pelicula) throw new Exception("Id:" + idPelicula + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        repository.deleteById(idPelicula);

    }

    @Override
    public PeliculaDTO mapPeliculaToPeliculaDTO(Pelicula pelicula) {

        PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(), pelicula.getTitulo(), pelicula.getFecha_creacion());

        return peliculaDTO;
    }

    @Override
    public void saveCharactersInMovie(Integer[] idPersonajes,Pelicula pelicula) throws Exception {
        List<Personaje> personajes = new LinkedList<>();
        for (Integer id : idPersonajes) {
            Boolean exists = personajeRepository.existsById(id);
            if (!exists) throw new Exception(ExceptionMessages.CHARACTER_NULL + " " + "ID:" + id);
            Personaje p = personajeService.getById(id);
            pelicula.getPersonajes().add(p);
            personajeRepository.save(p);
            repository.save(pelicula);
        }
    }


    @Override
    public PeliculaDTOResponse mapPeliculaToPeliculaDTOCompleta(Pelicula pelicula, List<PersonajeDTO>personajes) throws Exception {

        PeliculaDTOResponse peliculaDTOResponse = new PeliculaDTOResponse(pelicula.getImagen(),
                pelicula.getTitulo(), pelicula.getFecha_creacion(), pelicula.getCalificacion(),
                personajes, pelicula.getGenero());

        return peliculaDTOResponse;
    }

    @Override
    public List<PeliculaDTO> mapPeliculasToPeliculasDTO(List<Pelicula> peliculas) {
        List<PeliculaDTO> peliculasDto = new LinkedList<>();
        for (Pelicula pelicula : peliculas) {
            PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(), pelicula.getTitulo(), pelicula.getFecha_creacion());
            peliculasDto.add(peliculaDTO);
        }
        return peliculasDto;
    }

    @Override
    public PeliculaDTOResponse getDetailsPelicula(Integer idPelicula) throws Exception {

        if (idPelicula == null) throw new Exception(ExceptionMessages.ID_MOVIE_GET_DETAILS_NULL);
        Boolean exists = repository.existsById(idPelicula);
        if (!exists) throw new Exception(ExceptionMessages.MOVIE_NOT_FOUND);
        Pelicula pelicula = repository.getById(idPelicula);

        PeliculaDTOResponse peliculaDTOResponse = mapPeliculaToPeliculaDTOCompleta(pelicula,
                personajeService.mapPersonajesToPersonajesDTO(pelicula.getPersonajes()));

        return peliculaDTOResponse;

    }

    @Override
    public List<PeliculaDTO> searchPeliculas(String nombre, Integer idGenero, String orden) throws Exception {

        List<PeliculaDTO> peliculas = new LinkedList<>();
        if (nombre == null) throw new Exception(ExceptionMessages.TITLE_MOVIE_NULL);
        if (orden == null) throw new Exception(ExceptionMessages.ORDER_NULL);
        if (!nombre.isEmpty()) {
            peliculas = getPeliculasDTOByName(nombre);

        } else if (idGenero != null) {

            peliculas = getPeliculasDTOByFilterGenero(idGenero);

        } else peliculas = getPeliculas();

        if (!orden.isEmpty()) {

            peliculas = orderResultsPeliculasDTO(orden, peliculas);
        }
        return peliculas;
    }

    @Override
    public List<PeliculaDTO> getPeliculasDTOByName(String nombre) {
        List<Pelicula> peliculas = repository.findAllByName(nombre);
        return mapPeliculasToPeliculasDTO(peliculas);

    }

    @Override
    public List<PeliculaDTO> getPeliculasDTOByFilterGenero(Integer idGenero) {
        List<Pelicula> peliculas = repository.findAllByGenre(idGenero);

        return mapPeliculasToPeliculasDTO(peliculas);
    }

    @Override
    public List<PeliculaDTO> getPeliculas() {
        List<Pelicula> peliculas = repository.findAll();

        return mapPeliculasToPeliculasDTO(peliculas);
    }

    @Override
    public List<PeliculaDTO> orderResultsPeliculasDTO(String orden, List<PeliculaDTO> peliculasDTO) {
        if (orden.equals("ASC")) {
            peliculasDTO.sort(Comparator.comparing(PeliculaDTO::getFecha_creacion));
        } else {
            peliculasDTO.sort(Comparator.comparing(PeliculaDTO::getFecha_creacion).reversed());
        }
        return peliculasDTO;

    }

    @Override
    public void addPersonaje(Integer idPelicula, Integer idPersonaje) throws Exception {

        Pelicula pelicula = getById(idPelicula);
        Personaje personaje = personajeService.getById(idPersonaje);

        if (pelicula.getPersonajes().contains(personaje))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_CONTAINS);

        pelicula.getPersonajes().add(personaje);
        repository.save(pelicula);
        personajeRepository.save(personaje);

    }

    @Override
    public void removePersonaje(Integer idPelicula, Integer idPersonaje) throws Exception {
        Pelicula pelicula = getById(idPelicula);
        Personaje personaje = personajeService.getById(idPersonaje);
        if (!pelicula.getPersonajes().contains(personaje))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_NOT_CONTAINS);

        pelicula.getPersonajes().remove(personaje);
        repository.save(pelicula);
        personajeRepository.save(personaje);

    }
    */
}
