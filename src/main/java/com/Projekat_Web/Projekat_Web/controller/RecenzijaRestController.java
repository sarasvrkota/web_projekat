package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/recenzije")
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;


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

}
