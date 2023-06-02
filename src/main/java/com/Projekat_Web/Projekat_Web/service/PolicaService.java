package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
    public Polica save(Polica polica)
    {
        return this.policaRepository.save(polica);
    }
    public Polica findById(Long policaId) { return this.policaRepository.findById(policaId).orElse(null);}
}
