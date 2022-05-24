package com.backend.disney.ModelsDTO;

import com.backend.disney.Models.Genero;

import java.util.Date;
import java.util.List;

public class PeliculaDTOCompleta {

    private String imagen;
    private String titulo;
    private Date fecha_creacion;
    private Integer calificacion;
    private List<PersonajeDTO> personajes;
    private Genero genero;


    public PeliculaDTOCompleta(String imagen,String titulo,Date fecha_creacion,Integer calificacion,List<PersonajeDTO>personajes,Genero genero){
        this.imagen=imagen;
        this.titulo=titulo;
        this.fecha_creacion=fecha_creacion;
        this.calificacion=calificacion;
        this.personajes=personajes;
        this.genero=genero;
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

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public List<PersonajeDTO> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<PersonajeDTO> personajes) {
        this.personajes = personajes;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
