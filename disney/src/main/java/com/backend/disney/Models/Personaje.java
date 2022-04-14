package com.backend.disney.Models;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Personaje {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Integer id;

    @NotNull
    private String imagen;
    @NotNull
    private String nombre;
    @Column(nullable = true)
    private Integer edad;
    @Column(nullable = true)
    private Integer peso;
    @Column(nullable = true)
    private String historia;

    @ManyToMany(mappedBy = "personajes")
    public List<Pelicula> peliculas = new ArrayList<>();
}
