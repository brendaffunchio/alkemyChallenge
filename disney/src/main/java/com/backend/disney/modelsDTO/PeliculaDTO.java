package com.backend.disney.modelsDTO;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeliculaDTO {

    private String imagen;
    @NotBlank(message = "title cannot be blank")
    private String titulo;
    @NotBlank(message = "creation date cannot be blank")
    private Date fecha_creacion;
}
