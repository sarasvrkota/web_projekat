package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.dto.ZanrDto;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.Zanr;
import com.Projekat_Web.Projekat_Web.service.RecenzijaService;
import com.Projekat_Web.Projekat_Web.service.ZanrService;
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
}
