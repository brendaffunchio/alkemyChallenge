package com.backend.disney.Repositories;


import com.backend.disney.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,Integer> {

    @Query(value="SELECT * FROM USUARIO WHERE USERNAME = :username AND PASSWORD = :password", nativeQuery = true)
    Usuario findByEmailAndPassword(String username,String password);

    @Query(value="SELECT * FROM USUARIO WHERE USERNAME = :username", nativeQuery = true)
    Usuario findByEmail(String username);

}
