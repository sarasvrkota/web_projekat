package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.entity.*;
import com.Projekat_Web.Projekat_Web.repository.AutorRepository;
import com.Projekat_Web.Projekat_Web.repository.KnjigaRepository;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
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
    private RecenzijaService recenzijaService;

    @Autowired
    private KorisnikService korisnikService;

    @Autowired
    private StavkaPoliceService stavkaPoliceService;
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private PolicaRepository policaRepository;

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

        public Knjiga getById(Long id) { return this.knjigaRepository.getById(id);}

    public boolean obrisiKnjigu(Long id)
    {
        Knjiga knjiga = knjigaRepository.getById(id);

        List<RecenzijaDto> recenzije = recenzijaService.pronadjiRecenzije(knjiga.getNaslov());
        if (recenzije.isEmpty()) {

            List<Autor> autori = autorRepository.findAll();
            Autor autorKnjige = new Autor();
            for(Autor a : autori){
                if (a.getSpisakKnjiga().contains(knjiga)){
                    autorKnjige = a;
                }
            }
            autorKnjige.getSpisakKnjiga().remove(knjiga);
            autorRepository.save(autorKnjige);

            knjigaRepository.delete(knjiga);

            StavkaPolice stavkaPolice = this.stavkaPoliceService.getByKnjiga(knjiga);
            Polica polica = this.policaRepository.getByStavkaPolice(stavkaPolice);
            /*if(polica.getNaziv().equals("Read") || polica.getNaziv().equals("Currently Reading")
                    || polica.getNaziv().equals("Want to Read")) {



            }*/



            return true;
        }

        return false;

    }


    public boolean skiniKnjigu(Long id, String nazivPolice, Korisnik prijavljeniKorisnik)
    {

        Knjiga knjiga = knjigaRepository.getById(id);
        StavkaPolice stavkaPolice = stavkaPoliceService.getByKnjiga(knjiga);
        Polica p1 = policaRepository.getByStavkaPolice(stavkaPolice);


        if(prijavljeniKorisnik.getPolice().contains(stavkaPolice)) {

        Polica polica = policaRepository.getByNaziv(nazivPolice);
        if(polica.getId().equals(p1.getId())) {
           if (polica.getNaziv().equals("Read")) {
                stavkaPolice.setRecenzija(null);
           }
            polica.getStavkaPolice().remove(stavkaPolice);
            policaRepository.save(polica);
        } }




        return true;

    }








}
