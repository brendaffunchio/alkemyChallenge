package com.backend.disney.Repositories;

import com.backend.disney.Models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRolRepository extends JpaRepository<Rol,Integer> {

    @Query(value="SELECT * FROM ROL WHERE NAME = :name", nativeQuery = true)
    Rol findByName(String name);
}
