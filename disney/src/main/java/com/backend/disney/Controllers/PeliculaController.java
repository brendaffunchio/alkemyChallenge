package com.backend.disney.Controllers;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.Services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("movies")
public class PeliculaController {

    @Autowired
    private IPeliculaService service;

    @PostMapping(path = "/create")
    public Pelicula createMovie(@ModelAttribute Pelicula pelicula, @RequestParam ("file") MultipartFile imagen, @RequestParam ("genre")@Nullable String genero, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return service.createPelicula(pelicula,imagen,genero);
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }

    @PutMapping(path = "/edit")
    public Pelicula editMovie(@ModelAttribute Pelicula pelicula,@RequestParam ("file") MultipartFile imagen,@RequestParam ("genre")@Nullable String genero, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return service.updatePelicula(pelicula,imagen,genero);
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }
    @DeleteMapping (path = "/delete")
    public String deleteMovie(@RequestParam("id") Integer id ,HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
           service.deletePelicula(id);
           return "Movie deleted";
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage().toString();
        }
    }

@GetMapping(path="/getDetails")
public Pelicula getMovieDetails(@RequestParam ("id") Integer id ,HttpServletResponse httpServletResponse){
        try{
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return service.getDetailsPelicula(id);

        }catch(Exception e){
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }

}
    @GetMapping(path="/",consumes = "application/json", produces = "application/json")
    public List<PeliculaDTO> getMovies (@RequestParam (value = "name",required = false) String name,
                                        @RequestParam(value = "genre",required = false)Integer genre,
                                        @RequestParam("order")String order){

       return service.searchPeliculas(name,genre,order);
    }

    @PostMapping(path = "/{idMovie}/characters/{idCharacter}", consumes = "application/json", produces = "application/json")
    public String addCharacter(@PathVariable Integer idMovie,@PathVariable Integer idCharacter ,HttpServletResponse httpServletResponse)  {

        try{
            httpServletResponse.setStatus(HttpStatus.OK.value());
            service.addPersonaje(idMovie,idCharacter);
            return "Character added";
        }catch(Exception e){
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage().toString();
        }
    }
    @DeleteMapping(path = "/{movieId}/characters/{characterId}", consumes = "application/json", produces = "application/json")
    public String deleteCharacter(@PathVariable Integer movieId,@PathVariable Integer characterId ,HttpServletResponse httpServletResponse)  {

        try{
            httpServletResponse.setStatus(HttpStatus.OK.value());
            service.removePersonaje(movieId,characterId);
            return "Character deleted";
        }catch(Exception e){
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage().toString();
        }
    }

}