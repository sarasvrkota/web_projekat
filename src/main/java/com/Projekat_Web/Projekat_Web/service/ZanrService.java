package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.Zanr;
import com.Projekat_Web.Projekat_Web.repository.RecenzijaRepository;
import com.Projekat_Web.Projekat_Web.repository.ZanrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ZanrService

{

    @Autowired
    private ZanrRepository zanrRepository;
    public List<Zanr> findAll(){
        return zanrRepository.findAll();
    }
    public Zanr save(Zanr zanr)
    {
        return this.zanrRepository.save(zanr);
    }

}
