package com.backend.disney.ModelsDTO;

import com.sun.istack.NotNull;

public class PersonajeDTO {

    private String imagen;

    private String nombre;

    public PersonajeDTO(String imagen, String nombre){
        this.imagen=imagen;
        this.nombre=nombre;
    }
}
