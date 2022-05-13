package com.backend.disney.Services.impl;

import com.backend.disney.Exception.ExceptionMessages;
import com.backend.disney.Models.Genero;
import com.backend.disney.Models.Pelicula;
import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.Repositories.IPeliculaRepository;
import com.backend.disney.Repositories.IPersonajeRepository;
import com.backend.disney.Services.IGeneroService;
import com.backend.disney.Services.IPeliculaService;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PeliculaService implements IPeliculaService {

    @Autowired
    private IPeliculaRepository repository;

    @Autowired
    private IPersonajeRepository personajeRepository;

    @Autowired
    private IGeneroService generoService;

    @Override
    public Pelicula createPelicula(Pelicula pelicula, MultipartFile imagen, Integer genero) throws Exception {

        if (pelicula == null) throw new Exception(ExceptionMessages.MOVIE_NULL);
        if (pelicula.getTitulo().isEmpty()) throw new Exception(ExceptionMessages.TITLE_MOVIE_EMPTY);
        if (pelicula.getTitulo() == null) throw new Exception(ExceptionMessages.TITLE_MOVIE_NULL);
        if (pelicula.getFecha_creacion() == null) throw new Exception(ExceptionMessages.CREATION_DATE_MOVIE_NULL);
        if (imagen.isEmpty()) throw new Exception(ExceptionMessages.IMAGE_EMPTY);
        if(imagen==null)throw new Exception(ExceptionMessages.IMAGE_NULL);

        if (pelicula.getCalificacion() != null) {
            if (pelicula.getCalificacion() < 1 || pelicula.getCalificacion() > 5)
                throw new Exception(ExceptionMessages.QUALIFICATION_MOVIE_BAD);
        }
        if (genero == null) throw new Exception(ExceptionMessages.NAME_GENRE_EMPTY);
        Genero generoExistente = generoService.getById(genero);
        pelicula.setGenero(generoExistente);


        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = imagen.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);
        pelicula.setImagen(imagen.getOriginalFilename());

        repository.save(pelicula);
        return pelicula;


    }

    @Override
    public Pelicula updatePelicula(Pelicula pelicula, MultipartFile imagen, Integer genero) throws Exception {

        Boolean exists = repository.existsById(pelicula.getId());
        if (!exists) throw new Exception(ExceptionMessages.MOVIE_NOT_FOUND);

        Pelicula peliculaExistente = repository.getById(pelicula.getId());


        if (pelicula.getCalificacion() != null) {
            if (pelicula.getCalificacion() < 1 || pelicula.getCalificacion() > 5)
                throw new Exception(ExceptionMessages.QUALIFICATION_MOVIE_BAD);
        }
        if (pelicula.getCalificacion() != null) peliculaExistente.setCalificacion(pelicula.getCalificacion());
        if (!pelicula.getTitulo().isEmpty()) peliculaExistente.setTitulo(pelicula.getTitulo());
        if (!pelicula.getTitulo().isEmpty()) peliculaExistente.setFecha_creacion(pelicula.getFecha_creacion());

        if (genero != null) {
            Genero generoExistente = generoService.getById(genero);
            peliculaExistente.setGenero(generoExistente);
        }
        if (!imagen.isEmpty()) {

            Path directorioImagenes = Paths.get("src//main/resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            byte[] bytesImg = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);
            peliculaExistente.setImagen(imagen.getOriginalFilename());
        }
        repository.save(peliculaExistente);

        return peliculaExistente;


    }

    @Override
    public void deletePelicula(Integer idPelicula) throws Exception {
        if (idPelicula == null) throw new Exception("Cannot delete movie without id");
        Boolean pelicula = repository.existsById(idPelicula);
        if (!pelicula) throw new Exception("Id:" + idPelicula +"-> "+ ExceptionMessages.MOVIE_NOT_FOUND);

        repository.deleteById(idPelicula);

    }

    @Override
    public void mapPeliculaToPeliculaDTO(Pelicula pelicula) {
        Pelicula p = repository.getById(pelicula.getId());
        PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(), pelicula.getTitulo(), pelicula.getFecha_creacion());
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
    public Pelicula getDetailsPelicula(Integer idPelicula) throws Exception {
        if (idPelicula == null) throw new Exception("Cannot get detailes without id");
        Boolean exists = repository.existsById(idPelicula);
        if (!exists) throw new Exception(ExceptionMessages.MOVIE_NOT_FOUND);
        Pelicula pelicula = repository.getById(idPelicula);
        return pelicula;

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

        Boolean peliculaExists = repository.existsById(idPelicula);
        Boolean personajeExists = personajeRepository.existsById(idPersonaje);

        if (!peliculaExists || !personajeExists) throw new Exception("Cannot add character");

        Pelicula pelicula = repository.getById(idPelicula);
        Personaje personaje = personajeRepository.getById(idPersonaje);

        if(pelicula.getPersonajes().contains(personaje))throw new Exception(ExceptionMessages.MOVIE_CHARACTER_CONTAINS);

        pelicula.getPersonajes().add(personaje);
        repository.save(pelicula);
        personajeRepository.save(personaje);

    }

    @Override
    public void removePersonaje(Integer idPelicula, Integer idPersonaje) throws Exception {
        Boolean peliculaExists = repository.existsById(idPelicula);
        Boolean personajeExists = personajeRepository.existsById(idPersonaje);

        if (!peliculaExists || !personajeExists) throw new Exception("Cannot remove character");

        Pelicula pelicula = repository.getById(idPelicula);
        Personaje personaje = personajeRepository.getById(idPersonaje);

        pelicula.getPersonajes().remove(personaje);
        repository.save(pelicula);
        personajeRepository.save(personaje);

    }
}
