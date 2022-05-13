package com.backend.disney.ModelsDTO;

import java.util.ArrayList;
import java.util.List;

public class PersonajeDTOCompleto {

    private String imagen;
    private String nombre;
    private Integer edad;
    private Integer peso;
    private String historia;
    public List<PeliculaDTO> peliculas ;

    public PersonajeDTOCompleto(String imagen,String nombre,Integer edad,Integer peso,
                                String historia,List<PeliculaDTO> peliculas) {
        this.imagen=imagen;
        this.nombre=nombre;
        this.edad=edad;
        this.peso=peso;
        this.historia=historia;
        this.peliculas=peliculas;

    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public List<PeliculaDTO> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<PeliculaDTO> peliculas) {
        this.peliculas = peliculas;
    }
}
