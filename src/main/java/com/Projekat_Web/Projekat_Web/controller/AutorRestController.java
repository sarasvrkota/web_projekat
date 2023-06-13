package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.AutorDto;
import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.service.AutorService;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/autori")
public class AutorRestController {

    @Autowired
    private AutorService autorService;

    @Autowired
    private KnjigaService knjigaService;

    @Autowired
    private KorisnikService korisnikService;


    @PostMapping(value = "/dodavanje-knjige")
    public ResponseEntity<KnjigaDto> dodajKnjigu(@RequestBody KnjigaDto knjigaDto, HttpSession session) throws ChangeSetPersister.NotFoundException{

            Object imaSesiju = session.getAttribute("korisnik");
            Korisnik prijavljeniKorisnik = (Korisnik) imaSesiju;



            Knjiga knjiga = new Knjiga(knjigaDto.getNaslov(), knjigaDto.getNaslovnaFotografija(), knjigaDto.getISBN(),
                    knjigaDto.getDatumObjavljivanja(), knjigaDto.getBrojStrana(), knjigaDto.getOpis(),
                    knjigaDto.getZanr(), knjigaDto.getOcena());

            this.knjigaService.save(knjiga);

            Autor autorIzBaze = this.autorService.getById(prijavljeniKorisnik.getId());

            Set<Knjiga> knjige = new HashSet<>();
            knjige = autorIzBaze.getSpisakKnjiga();
            knjige.add(knjiga);

            autorIzBaze.setSpisakKnjiga(knjige);

            this.autorService.save(autorIzBaze);

            KnjigaDto knjigaDto1 = new KnjigaDto(knjiga);

            return new ResponseEntity<>(knjigaDto1, HttpStatus.OK);



    }

    @PutMapping(value = "/azuriranje-knjige/{naslovKnjige}")
    public ResponseEntity<KnjigaDto> azurirajKnjigu(@PathVariable(value = "naslovKnjige") String naslovKnjige, @RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Object imaSesiju = session.getAttribute("korisnik");
        Korisnik prijavljeniKorisnik = (Korisnik) imaSesiju;

        Autor autorIzBaze = this.autorService.getById(prijavljeniKorisnik.getId());

        Knjiga knjiga = this.knjigaService.getByNaslov(naslovKnjige);

        for(Knjiga k : autorIzBaze.getSpisakKnjiga()) {
            if(!knjiga.getId().equals(k.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        }
        knjiga.setNaslov(knjigaDto.getNaslov());
        knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
        knjiga.setISBN(knjigaDto.getISBN());
        knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
        knjiga.setBrojStrana(knjigaDto.getBrojStrana());
        knjiga.setOpis(knjigaDto.getOpis());
        knjiga.setZanr(knjigaDto.getZanr());
        knjiga.setOcena(knjigaDto.getOcena());

        this.knjigaService.save(knjiga);




        Set<Knjiga> knjige = new HashSet<>();
        knjige = autorIzBaze.getSpisakKnjiga();
        knjige.add(knjiga);

        autorIzBaze.setSpisakKnjiga(knjige);

        this.autorService.save(autorIzBaze);

        KnjigaDto knjigaDto1 = new KnjigaDto(knjiga);

        return new ResponseEntity<>(knjigaDto1, HttpStatus.OK);

    }


    @PutMapping("/azuriraj-autora/{id}")
    public ResponseEntity<?> azurirajProfilAutora(@PathVariable Long id, @RequestBody AutorDto autorDto,  HttpSession session) {
        Autor prijavljeniKorisnik = (Autor) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            autorService.azurirajProfilAutora(id, autorDto);

            return ResponseEntity.ok("Uspesno azuriran autor!");
        }
        return ResponseEntity.ok("Autor nije azuriran!");

    }




}
