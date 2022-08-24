package com.backend.disney.repositories;

import com.backend.disney.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IMovieRepository extends JpaRepository<Movie,String>{

    @Query(value="SELECT * FROM PELICULA WHERE titulo LIKE %:name%", nativeQuery = true)
    List<Movie> findAllByName(String name);

    @Query(value="SELECT * FROM PELICULA WHERE genero_id = :idGenero", nativeQuery = true)
    List<Movie> findAllByGenre(String idGenero);


}
