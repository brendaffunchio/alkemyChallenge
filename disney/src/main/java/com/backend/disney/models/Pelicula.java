package com.backend.disney.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Table(name="peliculas")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE peliculas SET soft_delete = true where id = ?")
@Where(clause = "soft_delete = false")
@Entity
public class Pelicula{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private Integer id;

    @NotNull
    @Column
    private String imagen;
    @NotNull
    @Column
    private String titulo;
    @NotNull
    @Column
    private Date fecha_creacion;

    @Column(nullable = true)
    private Integer calificacion;

    @Column
    private boolean soft_delete;

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
