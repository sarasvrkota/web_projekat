package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    public Korisnik findByKorisnickoIme(String korisnickoIme);
    public Optional<Korisnik> findById(Long id);
    public Korisnik save(Korisnik korisnik);
    public Korisnik getByMail(String mail);
    //public Korisnik findOne(Long id);


}
