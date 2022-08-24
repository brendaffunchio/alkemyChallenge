package com.backend.disney.mappers;

import com.backend.disney.models.Character;
import com.backend.disney.modelsDTO.MovieDTO;
import com.backend.disney.modelsDTO.CharacterDTO;
import com.backend.disney.modelsDTO.CharacterDTOComplete;
import com.backend.disney.repositories.ICharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CharacterMapper {

@Autowired
ICharacterRepository repository;

    public CharacterDTO mapCharacterToCharacterDTO(Character character) {

        CharacterDTO pDTO = new CharacterDTO(character.getImage(), character.getName());
        return pDTO;
    }


    public CharacterDTOComplete mapCharacterToCharacterDTOCompleto(Character character, List<MovieDTO> peliculas) {
        CharacterDTOComplete dtoComplete = new CharacterDTOComplete(character.getAge(), character.getWeight(), character.getHistory(), peliculas);
dtoComplete.setImage(character.getImage());
dtoComplete.setName(character.getName());
        return dtoComplete;

    }
public Character mapCharacterDTOCompleteToEntity(CharacterDTOComplete dto, String file){
        Character character = new Character();
        character.setName(dto.getName());
        character.setImage(file);
        character.setAge(dto.getAge());
        character.setWeight(dto.getWeight());
        character.setHistory(dto.getHistory());

        return character;
}


    public List<CharacterDTO> mapCharactersToCharactersDTO(List<Character> characters) {

        List<CharacterDTO> characterDTOS = new LinkedList<>();

        for (Character character : characters
        ) {
            CharacterDTO pDTO = new CharacterDTO(character.getImage(), character.getName());
            characterDTOS.add(pDTO);
        }
        return characterDTOS;
    }

}
