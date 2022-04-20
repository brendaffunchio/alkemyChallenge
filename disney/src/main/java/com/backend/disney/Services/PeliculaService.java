package com.backend.disney.Services;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.Repositories.IPeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeliculaService implements IPeliculaService{

    @Autowired
    private IPeliculaRepository repository;

    @Override
    public Pelicula createPelicula(Pelicula pelicula) throws Exception {
        if(pelicula!=null){
            repository.save(pelicula);
            mapPeliculaToPeliculaDTO(pelicula);
            return pelicula;
        }else throw new Exception("Cannot create movie");
 //falta guardar imagen
    }

    @Override
    public Pelicula updatePelicula(Pelicula pelicula) throws Exception {
      Pelicula peliculaExistente= repository.getById(pelicula.getId());
        if(peliculaExistente!=null){

            peliculaExistente.setImagen(pelicula.getImagen());
            peliculaExistente.setCalificación(pelicula.getCalificación());
            peliculaExistente.setTitulo(pelicula.getTitulo());
            peliculaExistente.setGenero(pelicula.getGenero());
            peliculaExistente.setFecha_creacion(pelicula.getFecha_creacion());
            repository.save(peliculaExistente);
            return peliculaExistente;
        }else throw new Exception("Cannot update movie");

        //falta guardar imagen
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
    public void mapPeliculasToPeliculasDTO(List<Pelicula> peliculas) {

    }

    @Override
    public Pelicula getDetailsPelicula(Integer idPelicula) throws Exception {

        Pelicula pelicula = repository.getById(idPelicula);
        if(pelicula !=null){
            return pelicula;
        }else throw new Exception("Cannot get movie");

    }

    @Override
    public List<PeliculaDTO> getPeliculasDTOByName(String nombre) {
        List<Pelicula> Peliculas= repository.findAllByName(nombre);
       //falta mapear

        return null;
    }

    @Override
    public List<PeliculaDTO> getPeliculasDTOByFilterGenero(Integer idGenero) {
        return null;
    }

    @Override
    public List<PeliculaDTO> orderResultsPeliculasDTO(String orden) {
        return null;
    }

    @Override
    public void addPersonaje(Integer idPelicula, Integer idPersonaje) {

    }
}
