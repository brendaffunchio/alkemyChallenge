package com.backend.disney.Controllers;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.Services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class PeliculaController {

    @Autowired
    private IPeliculaService service;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Pelicula createMovie(@RequestBody Pelicula pelicula) throws Exception {
        try {
            return service.createPelicula(pelicula);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(path = "/edit", consumes = "application/json", produces = "application/json")
    public Pelicula editMovie(@RequestBody Pelicula pelicula) throws Exception {
        try {
            return service.updatePelicula(pelicula);
        } catch (Exception e) {
            return null;
        }
    }
    @DeleteMapping (path = "/delete", consumes = "application/json", produces = "application/json")
    public String deleteMovie(@RequestParam("id") Integer id) throws Exception {
        try {
           service.deletePelicula(id);
           return "Movie deleted";
        } catch (Exception e) {
            return e.getMessage().toString();
        }
    }
   // @RequestParam ("folder_id")Integer folder_id

    @GetMapping(path="/",consumes = "application/json", produces = "application/json")
    public List<PeliculaDTO> getPeliculas (@RequestParam ("name") String nombre, @RequestParam("genre")Integer idGenero,@RequestParam("order")String orden){
        List<PeliculaDTO>peliculas=new LinkedList<>();
        if(nombre!=null){
            peliculas = service.getPeliculasDTOByName(nombre);

        }else if(idGenero!=null){

            peliculas = service.getPeliculasDTOByFilterGenero(idGenero);
        } else peliculas = service.getPeliculas();

        if(orden!=null){

          peliculas= service.orderResultsPeliculasDTO(orden,peliculas);
        }
        return peliculas;
    }

    
}