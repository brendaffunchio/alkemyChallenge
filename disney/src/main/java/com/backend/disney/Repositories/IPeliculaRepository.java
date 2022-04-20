package com.backend.disney.Repositories;

import com.backend.disney.Models.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPeliculaRepository extends JpaRepository<Pelicula,Integer>{

    @Query(value="SELECT * FROM PELICULA WHERE nombre = :name", nativeQuery = true)
    List<Pelicula> findAllByName(String name);

}
