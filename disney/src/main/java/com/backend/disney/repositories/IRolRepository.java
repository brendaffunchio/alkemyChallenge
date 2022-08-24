package com.backend.disney.repositories;

import com.backend.disney.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol,String> {

    @Query(value="SELECT * FROM ROL WHERE NAME = :name", nativeQuery = true)
    Rol findByName(String name);
}
