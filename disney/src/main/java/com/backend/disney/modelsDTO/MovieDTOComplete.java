package com.backend.disney.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTOComplete extends MovieDTO {

    @Range(min = 1, max = 5)
    private Integer qualification;
    @NotBlank(message="genre cannot be blank")
    private String genre;

    private List<CharacterDTO> characters;

}