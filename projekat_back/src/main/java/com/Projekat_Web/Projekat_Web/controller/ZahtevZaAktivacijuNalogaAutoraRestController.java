package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.RecenzijaDto;
import com.Projekat_Web.Projekat_Web.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora;
import com.Projekat_Web.Projekat_Web.service.AutorService;
import com.Projekat_Web.Projekat_Web.service.Generator;
import com.Projekat_Web.Projekat_Web.service.ZahtevZaAktivacijuNalogaAutoraService;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/zahtevi")
public class ZahtevZaAktivacijuNalogaAutoraRestController {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraService zahtevZaAktivacijuNalogaAutoraService;

    @Autowired
    private EmailRestController emailRestController;

    @Autowired
    private AutorService autorService;


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


    @PostMapping("/posalji-zahtev")
    public ResponseEntity<?> podnesiZahtevZaAktivaciju(@RequestBody ZahtevZaAktivacijuNalogaAutoraDto zahtevDto, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null ) {
            Korisnik loggedKorisnik = (Korisnik) session.getAttribute("employee");
            String provera = (String) session.getAttribute("tip");
            if(provera != null){
                System.out.println("Korisnik je prijavljen!!!");
            }

            zahtevZaAktivacijuNalogaAutoraService.podnesiZahtevZaAktivaciju(zahtevDto, prijavljeniKorisnik);
            return ResponseEntity.ok("Uspesno");
        }
        return ResponseEntity.ok("Nema korisnika");
    }

    @PostMapping("/odbij-zahtev/{id}")
    public ResponseEntity<?> odbijZahtev(@PathVariable("id") Long id, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            try {
                zahtevZaAktivacijuNalogaAutoraService.odbijZahtev(id, prijavljeniKorisnik);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.ok("Zahtev uspesno odbijen");
            }
        }
        return ResponseEntity.ok("Prijavljeni korisnik nije administrator");
    }

    @PostMapping("/prihvati-zahtev/{id}")
    public ResponseEntity<?> prihvatiZahtev(@PathVariable("id")  Long id, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            try {
                zahtevZaAktivacijuNalogaAutoraService.prihvatiZahtev(id, prijavljeniKorisnik);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.ok("Zahtev prihvacen");
            }
        }
        return ResponseEntity.ok("Prijavljeni korisnik nije admin!");
    }




   /* @PostMapping("/odbij-zahtev/{id}")
    public ResponseEntity<?> odbijZahtev(@PathVariable("id") Long id, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            try {

                ZahtevZaAktivacijuNalogaAutora zahtev2 = new ZahtevZaAktivacijuNalogaAutora();
                Autor a = autorService.getById(id);
                zahtevZaAktivacijuNalogaAutoraService.findByAutor(a);
                zahtev2.setPoruka(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getPoruka());
                zahtev2.setDatum(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getDatum());
                zahtev2.setTelefon(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getTelefon());
                zahtev2.setStatus(ZahtevZaAktivacijuNalogaAutora.Status.ODBIJEN);
                zahtev2.setAutor(a);
                zahtev2.setEmail(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getEmail());


                zahtevZaAktivacijuNalogaAutoraService.save(zahtev2);

                emailRestController.sendEmailReject(zahtev2.getEmail());

                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.ok("Zahtev odbijen");
            }
        }
        return ResponseEntity.ok("Prijavljeni korisnik nije administrator!");
    }*/

    @GetMapping(value = "/svi-zahtevi",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ZahtevZaAktivacijuNalogaAutoraDto>> pregledSvihZahteva(HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik.getUloga() == Korisnik.Uloga.ADMINISTRATOR) {

            List<ZahtevZaAktivacijuNalogaAutora> zahtevi = new ArrayList<>();

            zahtevi = this.zahtevZaAktivacijuNalogaAutoraService.findAll();

            List<ZahtevZaAktivacijuNalogaAutoraDto> zahtevDtos = new ArrayList<>();

            for (ZahtevZaAktivacijuNalogaAutora z : zahtevi) {
                if(z.getStatus() == ZahtevZaAktivacijuNalogaAutora.Status.NA_CEKANJU) {
                    ZahtevZaAktivacijuNalogaAutoraDto zahtevZaAktivacijuNalogaAutoraDto = new ZahtevZaAktivacijuNalogaAutoraDto(z.getEmail(),
                            z.getTelefon(), z.getPoruka(), z.getAutor().getId(), z.getStatus());

                    zahtevDtos.add(zahtevZaAktivacijuNalogaAutoraDto);
                }
            }
            return new ResponseEntity<>(zahtevDtos, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    /*@PostMapping("/api/zahtevi/{id}/prihvatiZahtev")
    public ResponseEntity<?> prihvatiZahtev(@PathVariable("id") Long id, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            try {
                ZahtevZaAktivacijuNalogaAutora zahtev2 = new ZahtevZaAktivacijuNalogaAutora();
                Autor a = autorService.getById(id);
                zahtevZaAktivacijuNalogaAutoraService.findByAutor(a);
                zahtev2.setPoruka(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getPoruka());
                zahtev2.setDatum(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getDatum());
                zahtev2.setTelefon(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getTelefon());
                zahtev2.setStatus(ZahtevZaAktivacijuNalogaAutora.Status.ODOBREN);
                zahtev2.setAutor(a);
                a.setJeAktivan(true);
                zahtev2.setEmail(zahtevZaAktivacijuNalogaAutoraService.findByAutor(a).getEmail());

                zahtevZaAktivacijuNalogaAutoraService.save(zahtev2);
                autorService.save(a);

                String email2 = zahtev2.getEmail();
                String lozinka = Generator.generisiLozinku();
                a.setLozinka(lozinka);

                emailRestController.sendEmailApproved(email2, lozinka);

                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.ok("uspesno prihvacen zahtev");
            }
        }
        return ResponseEntity.ok("nije admin");
    }*/





}
