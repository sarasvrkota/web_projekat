package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private PolicaRepository policaRepository;

    public Korisnik save(Korisnik korisnik)
    {
        return this.korisnikRepository.save(korisnik);
    }
    public Korisnik findByMail(String mail) { return this.korisnikRepository.findByMail(mail); }
    public Korisnik findByKorisnickoIme(String korisnickoIme)
    {
        return this.korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }
    public Optional<Korisnik> findById(Long id){
        return this.korisnikRepository.findById(id);
    }

    public Korisnik login(String mail, String lozinka) {
        Korisnik korisnik = korisnikRepository.findByMail(mail);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        return  korisnik;
    }

    /*public Korisnik findOne(Long id){
        Optional<Korisnik> foundEmployee = korisnikRepository.findById(id);
        if (foundEmployee.isPresent())
            return foundEmployee.get();

        return null;
    }*/

    public List<Korisnik> findByImeAndPrezime(String ime, String prezime) {
        return korisnikRepository.findByImeAndPrezime(ime, prezime);
    }
}
