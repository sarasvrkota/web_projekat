package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long> {

    List<StavkaPolice> findByKnjiga(Knjiga knjiga);

    public StavkaPolice save(StavkaPolice stavkaPolice);

    List<StavkaPolice> findAllByKnjiga(Knjiga knjiga);
    StavkaPolice getByKnjiga(Knjiga knjiga);
}
