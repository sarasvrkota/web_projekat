package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.repository.StavkaPoliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StavkaPoliceService {

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    @Autowired
    private PolicaService policaService;

   /* public List<StavkaPolice> findByKnjiga(Knjiga knjiga) {
        return stavkaPoliceRepository.findByKnjiga(knjiga);
    }*/
    public List<StavkaPolice> findByKnjiga(Knjiga knjiga) {
        return stavkaPoliceRepository.findByKnjiga(knjiga);
    }

    public StavkaPolice save(StavkaPolice stavkaPolice)
    {
        return this.stavkaPoliceRepository.save(stavkaPolice);
    }
    public List<StavkaPolice> findAllByKnjiga(Knjiga knjiga) {
        int i= 12;
        List<StavkaPolice> kknjige = stavkaPoliceRepository.findAllByKnjiga(knjiga);
        return this.stavkaPoliceRepository.findAllByKnjiga(knjiga);}

    public void deleteStavkaPolice(Long policaId, Long stavkaId) throws ChangeSetPersister.NotFoundException {
        StavkaPolice stavkaPolice = stavkaPoliceRepository.getById(stavkaId);
        Polica polica = policaService.getById(policaId);


        Recenzija recenzija = stavkaPolice.getRecenzija();


        polica.getStavkaPolice().remove(stavkaPolice);


        stavkaPoliceRepository.delete(stavkaPolice);


    }

}
