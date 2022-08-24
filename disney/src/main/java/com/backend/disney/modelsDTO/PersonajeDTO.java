package com.backend.disney.modelsDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonajeDTO {

    private String imagen;
    @NotBlank(message = "name cannot be blank")
    private String nombre;
}
