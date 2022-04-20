package com.backend.disney.Controllers;

import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import com.backend.disney.Services.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("characters")
public class PersonajeController {

    @Autowired
    private IPersonajeService service;

    @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
    public Personaje createCharacter(@RequestBody Personaje personaje,@RequestParam ("file") MultipartFile imagen) throws Exception {
        try {
            if(!imagen.isEmpty()){
          Path directorioImagenes = Paths.get("src//main/resources//static/images");
          String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

          byte[] bytesImg = imagen.getBytes();
          Path rutaCompleta = Paths.get(rutaAbsoluta+"//"+ imagen.getOriginalFilename());
                Files.write(rutaCompleta,bytesImg);
                personaje.setImagen(imagen.getOriginalFilename());
            }
            return service.createPersonaje(personaje);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(path = "/edit", consumes = "application/json", produces = "application/json")
    public Personaje editCharacter(@RequestBody Personaje personaje,@RequestParam ("file") MultipartFile imagen) throws Exception {
        try { if(!imagen.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main/resources//static/images");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

            byte[] bytesImg = imagen.getBytes();
            Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
            Files.write(rutaCompleta, bytesImg);
            personaje.setImagen(imagen.getOriginalFilename());
        }
            return service.updatePersonaje(personaje);
        } catch (Exception e) {
            return null;
        }
    }
    @DeleteMapping (path = "/delete", consumes = "application/json", produces = "application/json")
    public String deleteCharacter(@RequestParam("id") Integer id) throws Exception {
        try {
            service.deletePersonaje(id);
            return "Character deleted";
        } catch (Exception e) {
            return e.getMessage().toString();
        }
    }
    @GetMapping(path="/getDetails",consumes = "application/json", produces = "application/json")
    public Personaje getCharacterDetails(@RequestParam ("id") Integer id)throws Exception {
        try {
            return service.getDetailsPersonaje(id);
        } catch (Exception e) {
            return null;
        }
    }
    @GetMapping(path="/",consumes = "application/json", produces = "application/json")
    public List<PersonajeDTO> getCharacters (@RequestParam (value = "name",required = false) String name, @RequestParam(value = "age",required = false)Integer age,@RequestParam("weight")Integer weight, @RequestParam("movies")Integer movies){

       return service.searchPersonajes(name,age,weight,movies);

    }
}
