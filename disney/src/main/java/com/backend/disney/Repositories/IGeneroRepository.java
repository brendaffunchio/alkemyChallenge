package com.backend.disney.Repositories;

import com.backend.disney.Models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGeneroRepository extends JpaRepository<Genero,Integer> {

    @Query(value="SELECT * FROM GENERO WHERE NOMBRE = :name", nativeQuery = true)
    Genero findByName(String name);
}
