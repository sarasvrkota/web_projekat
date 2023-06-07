package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private  AutorRepository autorRepository;

    public Autor findByKorisnickoIme(String korisnickoIme)
    {
        return this.autorRepository.findByKorisnickoIme(korisnickoIme);
    }

    public Autor save(Autor autor)
    {
        return this.autorRepository.save(autor);
    }

    public Autor getById(Long id) { return this.autorRepository.getById(id);}



}
