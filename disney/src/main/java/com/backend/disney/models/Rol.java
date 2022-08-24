package com.backend.disney.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Getter
@Setter
@Table(name="roles")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rol{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
}
