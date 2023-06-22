package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import com.Projekat_Web.Projekat_Web.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/police")
public class PolicaRestController {

    @Autowired
    private PolicaService policaService;

    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private KnjigaService knjigaService;

    //@GetMapping
    @DeleteMapping("/{policaId}/knjiga/{knjigaId}")
    public ResponseEntity<?> deleteKnjigaCitalac(@PathVariable Long policaId, @PathVariable Long knjigaId, HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if(ulogovaniKorisnik.getUloga().equals(Korisnik.Uloga.CITALAC)) {
            knjigaService.deleteKnjiga(ulogovaniKorisnik.getId(), policaId, knjigaId);
            return new ResponseEntity<>("Knjiga uspesno skinuta sa police", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Nisi citalac!", HttpStatus.UNAUTHORIZED);
        }


    }






}
