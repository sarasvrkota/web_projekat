package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Long> {
}
