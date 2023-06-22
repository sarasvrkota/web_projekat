package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.LoginDto;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginRestController {
    @Autowired
    private KorisnikService korisnikService;
    @PostMapping("/api/korisnici/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session){
        // proverimo da li su podaci validni
        if((loginDto.getMail() != null && loginDto.getMail().isEmpty()) || loginDto.getLozinka().isEmpty())
            return new ResponseEntity("Neispravni podaci za prijavu", HttpStatus.BAD_REQUEST);

        Korisnik prijavljeniKorisnik = korisnikService.login(loginDto.getMail(), loginDto.getLozinka());
        if (prijavljeniKorisnik == null)
            return new ResponseEntity<>("Korisnik ne postoji!", HttpStatus.NOT_FOUND);


        session.setAttribute("korisnik", prijavljeniKorisnik);
        return ResponseEntity.ok("{\"message\": \"Uspesna prijava!\"}");
    }
}
