package com.backend.disney.models;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Table(name="Usuario")
@SQLDelete(sql = "UPDATE Usuario SET borrado = true where id = ?")
@Where(clause = "borrado = false")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private Integer id;

    @NotNull
    @Column(unique = true,name = "email")
    private String username;

    @NotNull
    @Column(name="contraseña")
    private String password;
    @Column(name="borrado")
    private boolean soft_delete;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;


}
