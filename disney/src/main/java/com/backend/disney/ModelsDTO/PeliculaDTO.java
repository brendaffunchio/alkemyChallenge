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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }
}
