package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.KorisnikDto;
import com.Projekat_Web.Projekat_Web.dto.ZanrDto;
import com.Projekat_Web.Projekat_Web.entity.*;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.ZanrService;
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

    @Autowired
    private ZanrService zanrService;


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

        if(knjigaDtos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(knjigaDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/prikaz-knjige/{naslov}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KnjigaDto> getKnjiga(@PathVariable String naslov) {

        Knjiga knjiga = knjigaService.getByNaslov(naslov);


        if(knjiga == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        KnjigaDto knjigaDto = new KnjigaDto(knjiga);

        return new ResponseEntity<>(knjigaDto, HttpStatus.OK);
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
        if(knjigaDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(knjigaDtos, HttpStatus.OK);

    }

    @PostMapping("/dodavanje-knjiga-admin")
    public ResponseEntity<?> dodajKnjigu(@RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            Knjiga knjiga = new Knjiga();
            knjiga.setNaslov(knjigaDto.getNaslov());
            knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
            knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
            knjiga.setBrojStrana(knjigaDto.getBrojStrana());
            knjiga.setOpis(knjigaDto.getOpis());
            knjiga.setISBN(knjigaDto.getISBN());

            ZanrDto zanrDto = new ZanrDto(knjigaDto.getZanr());
            Zanr zanr = new Zanr();
            zanr.setNaziv(zanrDto.getNaziv());
            //this.zanrService.save(zanr);

            knjiga.setZanr(zanr);

            knjiga.setOcena(knjigaDto.getOcena());

             knjigaService.save(knjiga);
            return ResponseEntity.ok(knjiga);
        }
        return ResponseEntity.ok("Prijavljeni korisnik nije administrator!");
    }

    @PutMapping("/azuriranje-knjiga-admin/{id}")
    public ResponseEntity<?> azurirajKnjigu(@PathVariable Long id, @RequestBody KnjigaDto knjigaDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {

            Knjiga knjiga = this.knjigaService.getById(id);
            knjiga.setNaslov(knjigaDto.getNaslov());
            knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
            knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
            knjiga.setBrojStrana(knjigaDto.getBrojStrana());
            knjiga.setOpis(knjigaDto.getOpis());
            knjiga.setISBN(knjigaDto.getISBN());

            ZanrDto zanrDto = new ZanrDto(knjigaDto.getZanr());
            Zanr zanr = new Zanr();
            zanr.setNaziv(zanrDto.getNaziv());
            this.zanrService.save(zanr);
            knjiga.setZanr(zanr);

            knjiga.setOcena(knjigaDto.getOcena());

            knjigaService.save(knjiga);
            return ResponseEntity.ok(knjiga);
        }
        return ResponseEntity.ok("Prijavljeni korisnik nije administrator!");
    }

    @DeleteMapping("/brisanje/{id}")
    public ResponseEntity<?> obrisiKnjiguPoNaslovu(@PathVariable("id") Long id, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            boolean obrisana = knjigaService.obrisiKnjigu(id);
            if (obrisana) {
                return ResponseEntity.ok("Uspesno obrisana knjiga!");
            }
            return ResponseEntity.ok("Knjiga nije obrisana!");
        }
        return ResponseEntity.ok("Prijavljeni korisnik nije administrator!");
    }

    @PostMapping("/skidanje-sa-police/{idKnjige}/{nazivPolice}")
    public ResponseEntity<?> skiniKnjiguSaPolice(@PathVariable("idKnjige") Long id,
                                                 @PathVariable String nazivPolice, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null) {
            boolean obrisana = knjigaService.skiniKnjigu(id, nazivPolice, prijavljeniKorisnik);
            if (obrisana) {
                return ResponseEntity.ok("Uspesno skinuta knjiga!");
            }
            return ResponseEntity.ok("Knjiga nije skinuta!");
        }
        return ResponseEntity.badRequest().build();

    }



}
