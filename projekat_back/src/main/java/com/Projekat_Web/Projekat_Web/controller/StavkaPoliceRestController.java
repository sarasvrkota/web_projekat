package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.RecenzijaService;
import com.Projekat_Web.Projekat_Web.service.StavkaPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/stavkapolice")
public class StavkaPoliceRestController {

    @Autowired
    private StavkaPoliceService stavkaPoliceService;

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private RecenzijaService recenzijaService;

    //@GetMapping("/recenzije/{naslov}")
    /*lic ResponseEntity<List<RecenzijaDto>> getRecenzijeKnjiga(@PathVariable String naslov) {
        // List<Knjiga> knjige = korisnikService.pregledajPoliceKorisnika(korisnikId);
        Knjiga knjiga = knjigaService.findByNaslov(naslov);

        Optional<StavkaPolice> optionalStavkaPolice = stavkaPoliceService.findByKnjiga(knjiga);
        if (optionalStavkaPolice.isPresent()) {
            StavkaPolice stavkaPolice = optionalStavkaPolice.get();

            List<Recenzija> recenzije = new ArrayList<>();
            recenzije = this.recenzijaService.findAll();

            List<RecenzijaDto> recenzijaDtos = new ArrayList<>();
            for(Recenzija r : recenzije) {
                 RecenzijaDto dto = new RecenzijaDto(r);
                 recenzijaDtos.add(dto);

            }
            return new ResponseEntity<>(recenzijaDtos, HttpStatus.OK);
        }




        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/




    @GetMapping("/recenzije/{naslov}")
    public ResponseEntity<List<RecenzijaDto>> getRecenzijeZaKnjigu(@PathVariable String naslov) {
        Knjiga knjiga = knjigaService.findByNaslov(naslov);
        List<StavkaPolice> stavke = stavkaPoliceService.findAllByKnjiga(knjiga);

        List<Recenzija> recenzije = new ArrayList<>();
        for (StavkaPolice stavka : stavke) {
            if (stavka.getRecenzija() != null) {
                recenzije.add(stavka.getRecenzija());
            }
        }

        List<RecenzijaDto> recenzijeDto = new ArrayList<>();
        for (Recenzija recenzija : recenzije) {
            RecenzijaDto dto = new RecenzijaDto(recenzija);
            recenzijeDto.add(dto);
        }

        if (recenzijeDto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(recenzijeDto, HttpStatus.OK);
    }


}
