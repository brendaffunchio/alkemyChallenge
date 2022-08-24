package com.backend.disney.models;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Table(name="Personaje")
@SQLDelete(sql = "UPDATE Personaje SET borrado = true where id = ?")
@Where(clause = "borrado = false")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Character {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private Integer id;

    @NotNull
    @Column(name = "imagen")
    private String image;
    @NotNull
    @Column(name="nombre")
    private String name;
    @Column(nullable = true,name="edad")
    private Integer age;
    @Column(nullable = true,name="peso")
    private Integer weight;
    @Nullable
    @Column(name="historia")
    private String history;
    @Column(name="borrado")
    private boolean soft_delete;

    @Nullable
    @ManyToMany(mappedBy = "characters")
    public List<Movie> movies = new ArrayList<>();
}
