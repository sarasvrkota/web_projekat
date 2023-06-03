package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.repository.StavkaPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StavkaPoliceService {

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

   /* public List<StavkaPolice> findByKnjiga(Knjiga knjiga) {
        return stavkaPoliceRepository.findByKnjiga(knjiga);
    }*/
    public Optional<StavkaPolice> findByKnjiga(Knjiga knjiga) {
        return stavkaPoliceRepository.findByKnjiga(knjiga);
    }

    public StavkaPolice save(StavkaPolice stavkaPolice)
    {
        return this.stavkaPoliceRepository.save(stavkaPolice);
    }

}
