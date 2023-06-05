package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    public void delete(Long id, Korisnik korisnik) {

        this.policaRepository.deleteById(id);
    }
    public Polica save(Polica polica)
    {
        return this.policaRepository.save(polica);
    }
    public Polica findById(Long policaId) { return this.policaRepository.findById(policaId).orElse(null);}

    public void deleteById(Long id) { this.policaRepository.deleteById(id);}
    public void delete(Polica polica) { this.policaRepository.delete(polica);}
    public Polica getById(Long id) { return this.policaRepository.getById(id);}

/*    public Polica findById(Long id) {
        Optional<Polica> policaOptional = policaRepository.findById(id);
        if (policaOptional.isPresent()) {
            return policaOptional.get();
        } else {
            throw new NoSuchElementException("Policu sa datim ID-om nije moguće pronaći");
        }
    }*/
}
