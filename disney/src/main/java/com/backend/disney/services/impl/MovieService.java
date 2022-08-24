package com.backend.disney.services.impl;

import com.backend.disney.exception.ExceptionMessages;
import com.backend.disney.mappers.CharacterMapper;
import com.backend.disney.mappers.MovieMapper;
import com.backend.disney.models.Genre;
import com.backend.disney.models.Movie;
import com.backend.disney.models.Character;
import com.backend.disney.modelsDTO.MovieDTO;
import com.backend.disney.modelsDTO.MovieDTOComplete;
import com.backend.disney.repositories.IMovieRepository;
import com.backend.disney.repositories.ICharacterRepository;
import com.backend.disney.services.IFileService;
import com.backend.disney.services.IGenreService;
import com.backend.disney.services.IMovieService;
import com.backend.disney.services.ICharacterService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class MovieService implements IMovieService {

    private IMovieRepository repository;
    private ICharacterRepository characterRepository;
    private ICharacterService characterService;
    private IGenreService genreService;
    private MovieMapper mapper;
    private CharacterMapper characterMapper;
    private IFileService fileService;


    @Autowired
    public MovieService(IMovieRepository repository, IFileService fileService, CharacterMapper characterMapper, MovieMapper mapper, ICharacterRepository characterRepository, ICharacterService characterService, IGenreService genreService) {
        this.repository = repository;
        this.characterRepository = characterRepository;
        this.characterService = characterService;
        this.genreService = genreService;
        this.mapper = mapper;
        this.characterMapper = characterMapper;
        this.fileService = fileService;
    }

    @Override
    public MovieDTOComplete create(MovieDTOComplete dto, MultipartFile file, String[] idCharacters) throws NotFoundException, IOException {
        Genre found = genreService.findByName(dto.getGenre());

        Movie toSave = mapper.mapMovieDTOCompleteToEntity(dto, found, fileService.saveFileInFolder(file));

        if (Arrays.stream(idCharacters).count() != 0) {
            saveCharactersInMovie(idCharacters, toSave);
        }

        Movie saved = repository.save(toSave);
        return mapper.mapMovieToMovieDTOComplete(saved, characterMapper.mapCharactersToCharactersDTO(saved.getCharacters()));
    }

    @Override
    public MovieDTOComplete update(MovieDTOComplete dto, String id, MultipartFile file) throws NotFoundException, IOException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException(ExceptionMessages.MOVIE_NOT_FOUND);
        Movie movieFound = repository.findById(id).get();

        movieFound.setQualification(dto.getQualification());
        movieFound.setTitle(dto.getTitle());
        movieFound.setCreation_date(dto.getCreation_date());

        Genre genreFound = genreService.findByName(dto.getGenre());
        movieFound.setGenre(genreFound);

        movieFound.setImage(fileService.saveFileInFolder(file));

        repository.save(movieFound);


        return mapper.mapMovieToMovieDTOComplete(movieFound,
                characterMapper.mapCharactersToCharactersDTO(movieFound.getCharacters()));

    }

    @Override
    public void delete(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException("Id:" + id + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        repository.deleteById(id);
    }

    @Override
    public Movie getEntityById(String id) throws NotFoundException {
        Boolean exists = repository.existsById(id);
        if (!exists) throw new NotFoundException("Id:" + id + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);

        return repository.findById(id).get();

    }

    @Override
    public MovieDTOComplete getDetailsMovie(String idMovie) throws NotFoundException {
        Boolean exists = repository.existsById(idMovie);
        if (!exists) throw new NotFoundException("Id:" + idMovie + "-> " + ExceptionMessages.MOVIE_NOT_FOUND);
        Movie found = repository.findById(idMovie).get();

        return mapper.mapMovieToMovieDTOComplete(found,characterMapper.mapCharactersToCharactersDTO(found.getCharacters()));

    }

    @Override
    public List<MovieDTO> searchMovies(String name, String idGenre, String order) {
        List<MovieDTO> movieDTOS = new ArrayList<>();

        if (!name.isEmpty()) {
            movieDTOS = mapper.mapMoviesToMoviesDTO(repository.findAllByName(name));

        } else if (idGenre != null) {

            movieDTOS = mapper.mapMoviesToMoviesDTO(repository.findAllByGenre(idGenre));

        } else movieDTOS = mapper.mapMoviesToMoviesDTO(repository.findAll());

        if (!order.isEmpty()) {

            movieDTOS = orderResultsMoviesDTO(order, movieDTOS);
        }
        return movieDTOS;
    }

    @Override
    public void addCharacter(String idMovie, String idCharacter) throws Exception {
        Movie movie = getEntityById(idMovie);
        Character character = characterService.getEntityById(idCharacter);

        if (movie.getCharacters().contains(character))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_CONTAINS);

        movie.getCharacters().add(character);
        repository.save(movie);
        characterRepository.save(character);
    }

    @Override
    public void removeCharacter(String idMovie, String idCharacter) throws Exception {
        Movie movie = getEntityById(idMovie);
        Character character = characterService.getEntityById(idCharacter);

        if (!movie.getCharacters().contains(character))
            throw new Exception(ExceptionMessages.MOVIE_CHARACTER_NOT_CONTAINS);

        movie.getCharacters().remove(character);
        repository.save(movie);
        characterRepository.save(character);
    }

    private void saveCharactersInMovie(String[] idCharacters, Movie movie) throws NotFoundException {
        for (String id : idCharacters) {
            Boolean exists = characterRepository.existsById(id);
            if (!exists) throw new NotFoundException(ExceptionMessages.CHARACTER_NOT_FOUND + " " + "ID:" + id);
            Character p = characterService.getEntityById(id);
            movie.getCharacters().add(p);
            characterRepository.save(p);
            repository.save(movie);
        }
    }

    private List<MovieDTO> orderResultsMoviesDTO(String order, List<MovieDTO> movieDTOS) {
        if (order.equals("ASC")) {
            movieDTOS.sort(Comparator.comparing(MovieDTO::getCreation_date));
        } else {
            movieDTOS.sort(Comparator.comparing(MovieDTO::getCreation_date).reversed());
        }
        return movieDTOS;
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
