package com.backend.disney.Repositories;

import com.backend.disney.Models.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonajeRepository extends JpaRepository<Personaje,Integer> {
}
