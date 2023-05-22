package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.KorisnikDto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Zanr;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/knjige")
public class KnjigaRestController {

    @Autowired
    private KnjigaService knjigaService;


    @GetMapping(value = "/pretraga",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KnjigaDto>> getKnjige(@RequestParam(value = "naslov") String naslov)
    {
        List<Knjiga> knjige = new ArrayList<>();

        knjige = this.knjigaService.findByNaslovContainingIgnoreCase(naslov);

        List<KnjigaDto> knjigaDtos = new ArrayList<>();

        for (Knjiga k : knjige) {

            KnjigaDto knjigaDto = new KnjigaDto(k.getNaslov(), k.getNaslovnaFotografija(), k.getISBN(),
                                                k.getDatumObjavljivanja(), k.getBrojStrana(), k.getOpis(),
                                                 k.getZanr(), k.getOcena());

            knjigaDtos.add(knjigaDto);

        }
        return new ResponseEntity<>(knjigaDtos, HttpStatus.OK);

    }

    @GetMapping(value = "/sveknjige",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KnjigaDto>> getSveKnjige()
    {
        List<Knjiga> knjige = new ArrayList<>();

        knjige = this.knjigaService.findAll();

        List<KnjigaDto> knjigaDtos = new ArrayList<>();

        for (Knjiga k : knjige) {

            KnjigaDto knjigaDto = new KnjigaDto(k.getNaslov(), k.getNaslovnaFotografija(), k.getISBN(),
                    k.getDatumObjavljivanja(), k.getBrojStrana(), k.getOpis(),
                    k.getZanr(), k.getOcena());

            knjigaDtos.add(knjigaDto);

        }
        return new ResponseEntity<>(knjigaDtos, HttpStatus.OK);

    }

}
