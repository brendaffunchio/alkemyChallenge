package com.backend.disney.Controllers;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.ModelsDTO.PeliculaDTO;
import com.backend.disney.Services.IPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("movies")
public class PeliculaController {

    @Autowired
    private IPeliculaService service;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Pelicula createMovie(@RequestBody Pelicula pelicula,@RequestParam ("file") MultipartFile imagen) throws Exception {
        try {
            if(!imagen.isEmpty()){
                Path directorioImagenes = Paths.get("src//main/resources//static/images");
                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+ imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytesImg);
                pelicula.setImagen(imagen.getOriginalFilename());
            }
            return service.createPelicula(pelicula);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(path = "/edit", consumes = "application/json", produces = "application/json")
    public Pelicula editMovie(@RequestBody Pelicula pelicula,@RequestParam ("file") MultipartFile imagen) throws Exception {
        try {
            if(!imagen.isEmpty()){
                Path directorioImagenes = Paths.get("src//main/resources//static/images");
                String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

                byte[] bytesImg = imagen.getBytes();
                Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+ imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytesImg);
                pelicula.setImagen(imagen.getOriginalFilename());
            }
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

@GetMapping(path="/getDetails",consumes = "application/json", produces = "application/json")
public Pelicula getMovieDetails(@RequestParam ("id") Integer id)throws Exception{
        try{
            return service.getDetailsPelicula(id);

        }catch(Exception e){
            return null;
        }

}
    @GetMapping(path="/",consumes = "application/json", produces = "application/json")
    public List<PeliculaDTO> getMovies (@RequestParam (value = "name",required = false) String name, @RequestParam(value = "genre",required = false)Integer genre,@RequestParam("order")String order){

       return service.searchPeliculas(name,genre,order);
    }

    @PostMapping(path = "/{idMovie}/characters/{idCharacter}", consumes = "application/json", produces = "application/json")
    public String addCharacter(@PathVariable Integer idMovie,@PathVariable Integer idCharacter) throws Exception {

        try{
            service.addPersonaje(idMovie,idCharacter);
            return "Character added";
        }catch(Exception e){
            return e.getMessage().toString();
        }
    }
    @DeleteMapping(path = "/{idMovie}/characters/{idCharacter}", consumes = "application/json", produces = "application/json")
    public String deleteCharacter(@PathVariable Integer idMovie,@PathVariable Integer idCharacter) throws Exception {

        try{
            service.removePersonaje(idMovie,idCharacter);
            return "Character deleted";
        }catch(Exception e){
            return e.getMessage().toString();
        }
    }

}