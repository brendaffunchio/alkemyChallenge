package com.backend.disney.services;

import com.backend.disney.models.Character;
import com.backend.disney.modelsDTO.CharacterDTO;
import com.backend.disney.modelsDTO.CharacterDTOComplete;
import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface ICharacterService {

    CharacterDTOComplete create(CharacterDTOComplete dto, MultipartFile file) throws IOException;
    CharacterDTOComplete update(CharacterDTOComplete dto, String id, MultipartFile file) throws NotFoundException, IOException;
    Character getEntityById(String id)throws NotFoundException;
    void delete(String id) throws NotFoundException;
    CharacterDTOComplete getDetails(String id) throws NotFoundException;
    List<CharacterDTO> searchCharacters(String name, Integer age, Integer weight, String idMovie) throws NotFoundException;
}
