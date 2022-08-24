package com.backend.disney.repositories;

import com.backend.disney.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,String> {

    @Query(value="SELECT * FROM ROL WHERE NAME = :name", nativeQuery = true)
    Role findByName(String name);
}
