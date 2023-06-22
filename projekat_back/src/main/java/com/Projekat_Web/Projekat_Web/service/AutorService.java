package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.dto.AutorDto;
import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.repository.AutorRepository;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    public Autor findByKorisnickoIme(String korisnickoIme) {
        return this.autorRepository.findByKorisnickoIme(korisnickoIme);
    }

    public Autor save(Autor autor) {
        return this.autorRepository.save(autor);
    }

    // public Autor findAutorByKorisnikId(Long id) { return this.autorRepository.findAutorByKorisnikId(id);}
    public Autor getById(Long id) {
        return this.autorRepository.getById(id);
    }


    public void azurirajProfilAutora(Long id, AutorDto autorDto) {
        Autor autor = autorRepository.getById(id);
        Korisnik korisnik = korisnikRepository.getById(id);
        if (autor.isJeAktivan() == false) {

            korisnik.setIme(autorDto.getIme());
            korisnik.setPrezime(autorDto.getPrezime());
            korisnik.setDatumRodjenja(autorDto.getDatumRodjenja());
            korisnik.setProfilnaSlika(autorDto.getProfilnaSlika());
            korisnik.setOpis(autorDto.getOpis());
            korisnik.setMail(autor.getMail());
            korisnik.setId(id);

            korisnikRepository.save(korisnik);

            autor.setIme(autorDto.getIme());
            autor.setPrezime(autorDto.getPrezime());
            autor.setMail(autorDto.getMail());
            autor.setKorisnickoIme(autorDto.getKorisnickoIme());
            autor.setDatumRodjenja(autorDto.getDatumRodjenja());
            autor.setProfilnaSlika(autorDto.getProfilnaSlika());
            autor.setOpis(autorDto.getOpis());
            autor.setUloga(autorDto.getUloga());
            autor.setJeAktivan(autorDto.isJeAktivan());

            autorRepository.save(autor);
        }


    }

}
