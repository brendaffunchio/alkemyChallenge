package com.backend.disney.services;


import com.backend.disney.models.Genero;
import com.backend.disney.modelsDTO.GeneroDTO;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface IGeneroService {

   Genero findByName(String nombre) throws NotFoundException;
   Genero getById(String id) throws NotFoundException;
   GeneroDTO createGenero(GeneroDTO genero, MultipartFile file) throws Exception;
}
