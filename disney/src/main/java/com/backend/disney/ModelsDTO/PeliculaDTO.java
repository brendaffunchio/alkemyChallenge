package com.backend.disney.ModelsDTO;

import com.sun.istack.NotNull;

import java.util.Date;

public class PeliculaDTO {
    private String imagen;

    private String titulo;

    private Date fecha_creacion;

    public PeliculaDTO(String imagen, String titulo, Date fecha_creacion){
        this.imagen=imagen;
        this.titulo=titulo;
        this.fecha_creacion=fecha_creacion;
    }
}
