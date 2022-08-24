package com.backend.disney.repositories;

import com.backend.disney.models.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IGeneroRepository extends JpaRepository<Genero,String> {

    @Query(value="SELECT * FROM GENERO WHERE nombre LIKE :name", nativeQuery = true)
    Genero findByName(String name);
}
