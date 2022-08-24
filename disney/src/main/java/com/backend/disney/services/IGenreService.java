package com.backend.disney.services;


import com.backend.disney.models.Genre;
import com.backend.disney.modelsDTO.GenreDTO;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

public interface IGenreService {

   Genre findByName(String name) throws NotFoundException;
   Genre getById(String id) throws NotFoundException;
   GenreDTO create(GenreDTO dto, MultipartFile file) throws Exception;
}
