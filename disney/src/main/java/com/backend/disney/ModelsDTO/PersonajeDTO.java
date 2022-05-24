package com.backend.disney.ModelsDTO;


public class PersonajeDTO {

    private String imagen;

    private String nombre;

    public PersonajeDTO(String imagen, String nombre){
        this.imagen=imagen;
        this.nombre=nombre;
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
}
