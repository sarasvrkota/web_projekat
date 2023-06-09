package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.dto.ZanrDto;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.Zanr;
import com.Projekat_Web.Projekat_Web.service.RecenzijaService;
import com.Projekat_Web.Projekat_Web.service.ZanrService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping(value = "/api/zanrovi")
public class ZanrRestController {


        @Autowired
        private ZanrService zanrService;


        @GetMapping(value = "/svi-zanrovi",produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<List<ZanrDto>> getSviZanrovi()
        {
            List<Zanr> zanrovi = new ArrayList<>();

            zanrovi = this.zanrService.findAll();

            List<ZanrDto> zanrDtos = new ArrayList<>();

            for (Zanr z : zanrovi) {

                ZanrDto zanrDto = new ZanrDto(z.getNaziv());

                zanrDtos.add(zanrDto);

            }
            return new ResponseEntity<>(zanrDtos, HttpStatus.OK);

        }

        @PostMapping("/dodavanje")
        public ResponseEntity<ZanrDto> dodajZanr(@RequestBody ZanrDto zanrDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga() == Korisnik.Uloga.ADMINISTRATOR) {

                    Zanr zanr = new Zanr();
                    zanr.setNaziv(zanrDto.getNaziv());

                    zanrService.save(zanr);
                    ZanrDto dto = new ZanrDto(zanr);

                    return new ResponseEntity<>(dto, HttpStatus.CREATED);

                //return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
}
