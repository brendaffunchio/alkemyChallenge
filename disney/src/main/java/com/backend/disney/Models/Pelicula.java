package com.backend.disney.Models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pelicula implements Serializable {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Integer id;

    @NotNull
    private String imagen;
    @NotNull
    private String titulo;
    @NotNull
    private Date fecha_creacion;

    @Range(min = 1,max = 5)
    @Column(nullable = true)
    private Integer calificacion;

    @ManyToMany()
    @JoinTable(name = "pelicula_personaje",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id")
    )
    private List<Personaje> personajes = new ArrayList<>();

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;


}
