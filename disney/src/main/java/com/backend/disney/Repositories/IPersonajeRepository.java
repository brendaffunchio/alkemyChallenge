package com.backend.disney.Repositories;

import com.backend.disney.Models.Pelicula;
import com.backend.disney.Models.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPersonajeRepository extends JpaRepository<Personaje,Integer> {


    @Query(value="SELECT * FROM PERSONAJE WHERE nombre LIKE %:name%", nativeQuery = true)
    List<Personaje> findAllByName(String name);

   /* @Query(value="SELECT * FROM PERSONAJE p " +
            "inner join pelicula_personaje pp on pp.personaje_id = p.id" +
            " WHERE p.edad = :edad"
            +"OR p.peso= :peso" + "OR pp.pelicula_id =:idPelicula", nativeQuery = true)
    List<Personaje> findAllByFilters(Integer edad, Integer peso, Integer idPelicula);*/
    @Query(value="SELECT * FROM PERSONAJE p  WHERE p.edad = :edad"
            +"OR p.peso= :peso" , nativeQuery = true)
    List<Personaje> findAllByFilter(Integer edad, Integer peso);
}
