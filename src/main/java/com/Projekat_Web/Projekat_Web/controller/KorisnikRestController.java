package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KorisnikDto;
import com.Projekat_Web.Projekat_Web.dto.LoginDto;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/korisnici")
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;

    @PostMapping(value = "/registracija",consumes = MediaType.APPLICATION_JSON_VALUE,
                produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDto> napraviKorisnika(@RequestBody KorisnikDto korisnikDto) throws Exception {


        Korisnik korisnik = new Korisnik(korisnikDto.getIme(),
                korisnikDto.getPrezime(), korisnikDto.getKorisnickoIme(), korisnikDto.getMail(), korisnikDto.getLozinka(),
                korisnikDto.getDatumRodjenja(), korisnikDto.getProfilnaSlika(), korisnikDto.getOpis(), korisnikDto.getUloga());



        Korisnik noviKorisnik= this.korisnikService.save(korisnik);


        KorisnikDto noviKorisnikDto = new KorisnikDto(noviKorisnik.getId(), noviKorisnik.getIme(), noviKorisnik.getPrezime(),
                noviKorisnik.getMail(), noviKorisnik.getDatumRodjenja(), noviKorisnik.getProfilnaSlika(),
                noviKorisnik.getOpis(), noviKorisnik.getUloga());
         // videti sa sarom sta da prosledjujemo korisniku

        return new ResponseEntity<>(noviKorisnikDto, HttpStatus.CREATED);

    }

   @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDto> getKorisnik(@PathVariable Long id)
    {
        /*if(uloga.equals("AUTOR"))
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }*/
        Optional<Korisnik> optKorisnik = this.korisnikService.findById(id);
        Korisnik korisnik = optKorisnik.get();

        KorisnikDto korisnikDto = new KorisnikDto(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime(), korisnik.getKorisnickoIme(),
                                korisnik.getMail(), korisnik.getLozinka(), korisnik.getDatumRodjenja(), korisnik.getProfilnaSlika(),
                                korisnik.getOpis(), korisnik.getUloga());

        return new ResponseEntity<>(korisnikDto, HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpSession session){
        // proverimo da li su podaci validni
        if(loginDto.getMail().isEmpty() || loginDto.getLozinka().isEmpty())
            return new ResponseEntity("Invalid login data", HttpStatus.BAD_REQUEST);

        Korisnik ulogovaniKorisnik = korisnikService.login(loginDto.getMail(), loginDto.getLozinka());
        if (ulogovaniKorisnik == null)
            return new ResponseEntity<>("User does not exist!", HttpStatus.NOT_FOUND);

        session.setAttribute("korisnik", ulogovaniKorisnik);
        return ResponseEntity.ok("Successfully logged in!");
    }

    @PostMapping("/logout")
    public ResponseEntity Logout(HttpSession session){
        Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (ulogovaniKorisnik == null)
            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Successfully logged out", HttpStatus.OK);
    }


}
