package com.backend.disney.models;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="genero")
@Entity
public class Genre {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private String id;
    @NotNull
    @Column(name="imagen")
    private String image;
    @NotNull
    @Column(unique = true,name="nombre")
    private String name;
}
