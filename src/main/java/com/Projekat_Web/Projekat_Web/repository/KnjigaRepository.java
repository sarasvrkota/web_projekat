package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnjigaRepository extends JpaRepository<Knjiga, Long> {

    Knjiga save(Knjiga knjiga);

    List<Knjiga> findByNaslovContainingIgnoreCase(String naslov);
    Knjiga findByNaslov(String naslov);
    //List<Knjiga> findByNaslovContainingIgnoreCase(String naziv);

     List<Knjiga> findAll();

     Knjiga getByNaslov(String naslov);
}
