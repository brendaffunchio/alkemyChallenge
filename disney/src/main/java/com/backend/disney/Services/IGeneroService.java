package com.backend.disney.Services;


import com.backend.disney.Models.Genero;
import org.springframework.web.multipart.MultipartFile;

public interface IGeneroService {

   Genero createGenero(Genero genero) throws Exception;
   Genero createGenero(Genero genero, MultipartFile imagen) throws Exception;
}
