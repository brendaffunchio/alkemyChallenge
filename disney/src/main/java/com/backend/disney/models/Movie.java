package com.backend.disney.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Table(name="pelicula")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE pelicula SET borrado = true where id = ?")
@Where(clause = "borrado = false")
@Entity
public class Movie {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private String id;

    @NotNull
    @Column(name="imagen")
    private String image;
    @NotNull
    @Column(name="titulo")
    private String title;
    @NotNull
    @Column(name="fecha_creacion")
    private Date creation_date;

    @Column(nullable = true,name="calificacion")
    private Integer qualification;

    @Column(name="borrado")
    private boolean soft_delete;

    @ManyToMany()
    @JoinTable(name = "pelicula_personaje",
            joinColumns = @JoinColumn(name = "pelicula_id"),
            inverseJoinColumns = @JoinColumn(name = "personaje_id")
    )
    private List<Character> characters = new ArrayList<>();

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "genero_id", nullable = false)
    private Genre genre;


}
