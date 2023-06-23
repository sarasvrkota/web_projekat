package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {

    public Korisnik findByKorisnickoIme(String korisnickoIme);
    public Optional<Korisnik> findById(Long id);
    public Korisnik save(Korisnik korisnik);
    public Korisnik getById(Long id);
    public Korisnik findByMail(String mail);
    //public Korisnik findOne(Long id);

   // @Query("SELECT k FROM Korisnik k WHERE k.ime = :ime AND k.prezime = :prezime")
    List<Korisnik> findByImeAndPrezime( String ime, String prezime);


    Korisnik getByMail(String mejlAdresa);
}
