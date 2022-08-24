package com.backend.disney.repositories;


import com.backend.disney.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,String> {

    @Query(value="SELECT * FROM USUARIO WHERE USERNAME = :username AND PASSWORD = :password", nativeQuery = true)
    Usuario findByEmailAndPassword(String username,String password);

    @Query(value="SELECT * FROM USUARIO WHERE USERNAME = :username", nativeQuery = true)
    Usuario findByEmail(String username);

}
