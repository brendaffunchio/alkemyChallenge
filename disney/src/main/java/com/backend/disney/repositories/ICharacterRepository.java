package com.backend.disney.repositories;

import com.backend.disney.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICharacterRepository extends JpaRepository<Character,String> {

    @Query(value="SELECT * FROM PERSONAJE WHERE nombre LIKE %:name%", nativeQuery = true)
    List<Character> findAllByName(String name);

    @Query(value="SELECT * FROM PERSONAJE p  WHERE p.edad = :edad", nativeQuery = true)
    List<Character> findAllByAge(Integer edad);
    @Query(value="SELECT * FROM PERSONAJE p  WHERE p.peso = :peso", nativeQuery = true)
    List<Character> findAllByWeight(Integer peso);
}
