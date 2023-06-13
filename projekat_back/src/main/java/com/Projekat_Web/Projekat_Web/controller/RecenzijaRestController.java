package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.RecenzijaService;
import com.Projekat_Web.Projekat_Web.service.StavkaPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/recenzije")
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private StavkaPoliceService stavkaPoliceService;


    @GetMapping(value = "/sverecenzije",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecenzijaDto>> getSveRecenzije()
    {
        List<Recenzija> recenzije = new ArrayList<>();

        recenzije = this.recenzijaService.findAll();

        List<RecenzijaDto> recenzijaDtos = new ArrayList<>();

        for (Recenzija r : recenzije) {

            RecenzijaDto recenzijaDto = new RecenzijaDto(r.getId(), r.getOcena(), r.getTekst(), r.getDatumRecenzije(), r.getKorisnik());

            recenzijaDtos.add(recenzijaDto);

        }
        return new ResponseEntity<>(recenzijaDtos, HttpStatus.OK);

    }

    /*@GetMapping("/pregled-recenzija-knjige/{naslov}")
    public ResponseEntity<List<RecenzijaDto>> pregledajRecenzije(@PathVariable("naslov") String naslov) {
        // Pronalaženje knjige
        Knjiga knjiga = knjigaService.getByNaslov(naslov);
        if (knjiga == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Pronalaženje stavki police koje sadrže traženu knjigu
        List<StavkaPolice> stavkePolice = stavkaPoliceService.findByKnjiga(knjiga);


        // Izvlačenje recenzija iz stavki police
        List<RecenzijaDto> recenzijeDto = new ArrayList<>();
        for (StavkaPolice stavka : stavkePolice) {
            Recenzija recenzija = stavka.getRecenzija();
            if (recenzija != null) {
                RecenzijaDto recDto = new RecenzijaDto(recenzija);
                recenzijeDto.add(recDto);
            }
        }

        if (recenzijeDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(recenzijeDto, HttpStatus.OK);
    }
*/
}
