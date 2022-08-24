package com.backend.disney.repositories;

import com.backend.disney.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface IGenreRepository extends JpaRepository<Genre,String> {

    @Query(value="SELECT * FROM GENERO WHERE nombre LIKE :name", nativeQuery = true)
    Genre findByName(String name);
}
