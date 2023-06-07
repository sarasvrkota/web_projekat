package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Polica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PolicaRepository extends JpaRepository<Polica, Long> {
    public Polica save(Polica polica);
    public Polica findByNaziv(String naziv);


    public Optional<Polica> findById(Long policaId);

    public void deleteById(Long id);
    //public void delete(Polica polica);
    public Polica getById(Long id);
    Polica getByNaziv(String naziv);
}
