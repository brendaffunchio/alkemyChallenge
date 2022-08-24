package com.backend.disney.models;


import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Table(name="usuarios")
@SQLDelete(sql = "UPDATE usuarios SET soft_delete = true where id = ?")
@Where(clause = "soft_delete = false")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario{

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    @Column
    private String password;
    @Column
    private boolean soft_delete;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    private Rol role;


}
