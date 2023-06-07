package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    public Autor findByKorisnickoIme(String korisnickoIme);

    Autor save(Autor autor);

    Autor getById(Long id);
}
