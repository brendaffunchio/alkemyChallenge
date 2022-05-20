package com.backend.disney.Controllers;

import com.backend.disney.Models.Personaje;
import com.backend.disney.ModelsDTO.PersonajeDTO;
import com.backend.disney.ModelsDTO.PersonajeDTOCompleto;
import com.backend.disney.Services.IPersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping(path = "/create")
    public PersonajeDTOCompleto createCharacter(@ModelAttribute Personaje personaje, @RequestParam ("file") MultipartFile imagen, HttpServletResponse httpServletResponse) {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return service.createPersonaje(personaje,imagen);
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }

    @PutMapping(path = "/edit")
    public PersonajeDTOCompleto editCharacter(@ModelAttribute Personaje personaje,@RequestParam ("file") MultipartFile imagen,HttpServletResponse httpServletResponse) {
       try{
           httpServletResponse.setStatus(HttpStatus.OK.value());
            return service.updatePersonaje(personaje,imagen);
        } catch (Exception e) {
           httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }
    @DeleteMapping (path = "/delete")
    public String deleteCharacter(@RequestParam(value="id",required = false) Integer id,HttpServletResponse httpServletResponse)  {
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            service.deletePersonaje(id);
            return "Character deleted";
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return e.getMessage().toString();
        }
    }
    @GetMapping(path="/getDetails")
    public PersonajeDTOCompleto getCharacterDetails(@RequestParam (value="id",required = false) Integer id,HttpServletResponse httpServletResponse){
        try {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return service.getDetailsPersonaje(id);
        } catch (Exception e) {
            httpServletResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            return null;
        }
    }
    @GetMapping(path="/")
    public List<PersonajeDTO> getCharacters (@RequestParam (value = "name",required = false) String name, @RequestParam(value = "age",required = false)Integer age,@RequestParam(value = "weight",required = false)Integer weight, @RequestParam(value="movies",required = false)Integer movies) throws Exception {

       return service.searchPersonajes(name,age,weight,movies);

    }
}
