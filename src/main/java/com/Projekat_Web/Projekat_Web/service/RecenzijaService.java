package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.entity.*;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import com.Projekat_Web.Projekat_Web.repository.RecenzijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Projekat_Web.Projekat_Web.dto.PolicaDto;
import  com.Projekat_Web.Projekat_Web.dto.StavkaPoliceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RecenzijaService {

    @Autowired
    private RecenzijaRepository recenzijaRepository;
    @Autowired
    private PolicaRepository policaRepository;

    public List<Recenzija> findAll(){
        return recenzijaRepository.findAll();
    }

    public Recenzija save(Recenzija recenzija)
    {
        return this.recenzijaRepository.save(recenzija);
    }

    public List<RecenzijaDto> pronadjiRecenzije(String naslovKnjige) {
        List<RecenzijaDto> recenzije = new ArrayList<>();

        List<Polica> police = policaRepository.findAll();
        for (Polica polica : police) {
            PolicaDto p = new PolicaDto(polica);
            Set<StavkaPolice> stavke = p.getStavkaPolice();
            for (StavkaPolice stavka : stavke) {
                if (stavka.getKnjiga().getNaslov().equals(naslovKnjige)) {
                    Recenzija r = stavka.getRecenzija();

                    RecenzijaDto recenzija = new RecenzijaDto(r);
                    if (recenzija != null) {
                        recenzije.add(recenzija);
                    }
                }
            }
        }
        return recenzije;
    }




}
