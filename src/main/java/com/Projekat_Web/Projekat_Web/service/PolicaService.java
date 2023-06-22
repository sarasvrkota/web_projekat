package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.repository.KnjigaRepository;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import com.Projekat_Web.Projekat_Web.repository.StavkaPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    public void delete(Long id, Korisnik korisnik) {

        this.policaRepository.deleteById(id);
    }
    public Polica save(Polica polica)
    {
        return this.policaRepository.save(polica);
    }
    public Polica findById(Long policaId) { return this.policaRepository.findById(policaId).orElse(null);}

    public void deleteById(Long id) { this.policaRepository.deleteById(id);}
    public void delete(Polica polica) { this.policaRepository.delete(polica);}
    public Polica getById(Long id) { return this.policaRepository.getById(id);}
    public Polica getByNaziv(String naziv) { return this.policaRepository.getByNaziv(naziv);}

/*    public Polica findById(Long id) {
        Optional<Polica> policaOptional = policaRepository.findById(id);
        if (policaOptional.isPresent()) {
            return policaOptional.get();
        } else {
            throw new NoSuchElementException("Policu sa datim ID-om nije moguće pronaći");
        }
    }*/

    public Long addKnjigaOnPolica(Long policaId, String knjigaId) throws ChangeSetPersister.NotFoundException {
        Polica polica = policaRepository.findById(policaId).orElse(null);
        Knjiga knjiga = knjigaRepository.getByNaslov(knjigaId);
        StavkaPolice stavkaPolice = new StavkaPolice();

        stavkaPolice.setKnjiga(knjiga);
        stavkaPoliceRepository.save(stavkaPolice);
        polica.getStavkaPolice().add(stavkaPolice);
        Set<StavkaPolice> sp = new HashSet<>();
        sp.add(stavkaPolice);
        polica.setStavkaPolice(sp);
        save(polica);

        return stavkaPolice.getId();
    }
}
