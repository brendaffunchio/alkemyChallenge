package com.backend.disney.Services;

import com.backend.disney.Models.Genero;
import com.backend.disney.Repositories.IGeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneroService implements IGeneroService{

 @Autowired
 private IGeneroRepository repository;

    @Override
    public void createGenero(Genero genero) throws Exception {
 if(genero != null) {
     repository.save(genero);
 }else throw new Exception("Cannot create genre");
    }
}
