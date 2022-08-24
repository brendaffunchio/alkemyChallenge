package com.backend.disney.modelsDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeDTOCompleta extends PersonajeDTO{

    private Integer edad;
    private Integer peso;
    private String historia;
    public List<PeliculaDTO> peliculas ;


}
