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
public class PeliculaDTOCompleta extends PeliculaDTO{

    @Range(min = 1, max = 5)
    @NotBlank(message = "qualification cannot be blank")
    private Integer calificacion;
    @NotBlank(message="genre cannot be blank")
    private String genero;

    private List<PersonajeDTO> personajes;

}