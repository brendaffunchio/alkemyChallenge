package com.backend.disney.repositories;


import com.backend.disney.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {

    @Query(value="SELECT * FROM USUARIO WHERE USERNAME = :username AND PASSWORD = :password", nativeQuery = true)
    User findByEmailAndPassword(String username, String password);

    @Query(value="SELECT * FROM USUARIO WHERE USERNAME = :username", nativeQuery = true)
    User findByEmail(String username);

}
