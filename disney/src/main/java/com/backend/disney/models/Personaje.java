package com.backend.disney.models;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Table(name="personajes")
@SQLDelete(sql = "UPDATE personajes SET soft_delete = true where id = ?")
@Where(clause = "soft_delete = false")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Personaje{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private Integer id;

    @NotNull
    @Column
    private String imagen;
    @NotNull
    @Column
    private String nombre;
    @Column(nullable = true)
    private Integer edad;
    @Column(nullable = true)
    private Integer peso;
    @Nullable
    @Column
    private String historia;
    @Column
    private boolean soft_delete;

    @Nullable
    @ManyToMany(mappedBy = "personajes")
    public List<Pelicula> peliculas = new ArrayList<>();
}
