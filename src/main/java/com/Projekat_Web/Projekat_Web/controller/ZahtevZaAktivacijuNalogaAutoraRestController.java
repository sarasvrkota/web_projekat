package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora;
import com.Projekat_Web.Projekat_Web.service.ZahtevZaAktivacijuNalogaAutoraService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/zahtevzaaktivaciju")
public class ZahtevZaAktivacijuNalogaAutoraRestController {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraService zahtevZaAktivacijuNalogaAutoraService;


    //napraviti sutra i
    @PostMapping("/zahtev")
    public ResponseEntity<String> login(@RequestBody ZahtevZaAktivacijuNalogaAutoraDto zahtevDto, HttpSession session){
        // proverimo da li su podaci validni
        if(zahtevDto.getEmail().isEmpty() || zahtevDto.getTelefon().isEmpty())
            return new ResponseEntity("Invalid login data", HttpStatus.BAD_REQUEST);

        ZahtevZaAktivacijuNalogaAutora zahtev = zahtevZaAktivacijuNalogaAutoraService.login(zahtevDto.getEmail(), zahtevDto.getTelefon());
        if (zahtev != null)
            return new ResponseEntity<>("Postojeci korisnik", HttpStatus.NOT_FOUND);

        session.setAttribute("employee", zahtev);
        return ResponseEntity.ok("Successfully logged in!");
    }
}
