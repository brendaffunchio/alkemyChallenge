package com.backend.disney.Services;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.Repositories.IPeliculaRepository;
import com.backend.disney.Repositories.IPersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class PeliculaService implements IPeliculaService{

    @Autowired
    private IPeliculaRepository repository;

    @Autowired
    private IPersonajeRepository personajeRepository;

    @Override
    public Pelicula createPelicula(Pelicula pelicula) throws Exception {
        if(pelicula!=null){
            repository.save(pelicula);
           return pelicula;
        }else throw new Exception("Cannot create movie");

    }

    @Override
    public Pelicula updatePelicula(Pelicula pelicula) throws Exception {
      Pelicula peliculaExistente= repository.getById(pelicula.getId());
        if(peliculaExistente!=null){

            peliculaExistente.setCalificación(pelicula.getCalificación());
            peliculaExistente.setTitulo(pelicula.getTitulo());
            peliculaExistente.setGenero(pelicula.getGenero());
            peliculaExistente.setFecha_creacion(pelicula.getFecha_creacion());
            repository.save(peliculaExistente);
            return peliculaExistente;
        }else throw new Exception("Cannot update movie");


    }

    @Override
    public void deletePelicula(Integer idPelicula) throws Exception {
      Pelicula pelicula= repository.getById(idPelicula);
 if(pelicula!=null){
     repository.delete(pelicula);
 }else throw new Exception("Cannot delete movie");
    }

    @Override
    public void mapPeliculaToPeliculaDTO(Pelicula pelicula) {
          Pelicula p= repository.getById(pelicula.getId());
          PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(),pelicula.getTitulo(),pelicula.getFecha_creacion());
    }

    @Override
    public List<PeliculaDTO> mapPeliculasToPeliculasDTO(List<Pelicula> peliculas) {
List<PeliculaDTO> peliculasDto = new LinkedList<>();
        for (Pelicula pelicula:peliculas)
        {
            PeliculaDTO peliculaDTO = new PeliculaDTO(pelicula.getImagen(),pelicula.getTitulo(),pelicula.getFecha_creacion());
peliculasDto.add(peliculaDTO);
        }
        return peliculasDto;
    }

    @Override
    public Pelicula getDetailsPelicula(Integer idPelicula) throws Exception {

        Pelicula pelicula = repository.getById(idPelicula);
        if(pelicula !=null){
            return pelicula;
        }else throw new Exception("Cannot get movie");

    }

    @Override
    public List<PeliculaDTO> searchPeliculas(String nombre, Integer idGenero, String orden) {
        List<PeliculaDTO>peliculas=new LinkedList<>();
        if(nombre!=null){
            peliculas = getPeliculasDTOByName(nombre);

        }else if(idGenero!=null){

            peliculas = getPeliculasDTOByFilterGenero(idGenero);
        } else peliculas = getPeliculas();

        if(orden!=null){

            peliculas= orderResultsPeliculasDTO(orden,peliculas);
        }
        return peliculas;
    }

    @Override
    public List<PeliculaDTO> getPeliculasDTOByName(String nombre) {
        List<Pelicula> peliculas= repository.findAllByName(nombre);
       return mapPeliculasToPeliculasDTO(peliculas);

    }

    @Override
    public List<PeliculaDTO> getPeliculasDTOByFilterGenero(Integer idGenero) {
        List <Pelicula> peliculas = repository.findAllByGenre(idGenero);

        return mapPeliculasToPeliculasDTO(peliculas);
    }

    @Override
    public List<PeliculaDTO> getPeliculas() {
        List <Pelicula> peliculas = repository.findAll();

        return mapPeliculasToPeliculasDTO(peliculas);
    }

    @Override
    public List<PeliculaDTO> orderResultsPeliculasDTO(String orden, List<PeliculaDTO>peliculasDTO) {

       // peliculasDTO.sort();
        return null;
    }

    @Override
    public void addPersonaje(Integer idPelicula, Integer idPersonaje) throws Exception {
Pelicula pelicula = repository.getById(idPelicula);
Personaje personaje= personajeRepository.getById(idPersonaje);
if (pelicula!=null && personaje!=null){
    pelicula.getPersonajes().add(personaje);
    repository.save(pelicula);
    personajeRepository.save(personaje);
} else throw new Exception("Cannot add character");
    }

    @Override
    public void removePersonaje(Integer idPelicula, Integer idPersonaje) throws Exception {
        Pelicula pelicula = repository.getById(idPelicula);
        Personaje personaje= personajeRepository.getById(idPersonaje);
        if (pelicula!=null && personaje!=null){
            pelicula.getPersonajes().remove(personaje);
            repository.save(pelicula);
            personajeRepository.save(personaje);
        } else throw new Exception("Cannot remove character");
    }
}
