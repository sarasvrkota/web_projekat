package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private KorisnikService korisnikService;

    public List<Knjiga> findAll(){
        return knjigaRepository.findAll();
    }


    public Knjiga save(Knjiga knjiga)
    {
        return this.knjigaRepository.save(knjiga);
    }
    public KnjigaService(KnjigaRepository knjigaRepository) {
        this.knjigaRepository = knjigaRepository;
    }

    public List<Knjiga> findByNaslovContainingIgnoreCase(String naslov) {
        return knjigaRepository.findByNaslovContainingIgnoreCase(naslov);
    }

    public Knjiga findByNaslov(String naslov) {
        return knjigaRepository.findByNaslov(naslov);
    }

    public Knjiga getByNaslov(String naslov) {
        return knjigaRepository.getByNaslov(naslov);
    }
        public boolean findKnjigaOnPrimarna(Long citalacId, String knjigaId){
            Korisnik korisnik = korisnikService.findOne(citalacId);
            Set<Polica> korisnikovePolice = korisnik.getPolice();

            for (Polica p : korisnikovePolice) {
                if(p.isPrimarna()){
                    for (StavkaPolice stavka : p.getStavkaPolice()) {
                        if (stavka.getKnjiga().getNaslov() == knjigaId) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }








}
