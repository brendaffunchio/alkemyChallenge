package com.backend.disney.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDTOComplete extends CharacterDTO {

    private Integer age;
    private Integer weight;
    @Length(max=255)
    private String history;
    public List<MovieDTO> movies;


}
