package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.KorisnikDto;
import com.Projekat_Web.Projekat_Web.dto.LoginDto;
import com.Projekat_Web.Projekat_Web.dto.PolicaDto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/korisnici")
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;


    @PostMapping(value = "/registracija",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonIgnoreProperties(value = {"lozinka", "potvrdaLozinke"})
    public ResponseEntity<KorisnikDto> napraviKorisnika(@RequestBody KorisnikDto korisnikDto) throws Exception {

        if (korisnikService.findByMail(korisnikDto.getMail()) != null) {
            throw new Exception("Adresa mora biti jedinstvena - korisnik vec postoji");
        }

        if (korisnikService.findByKorisnickoIme(korisnikDto.getKorisnickoIme()) != null) {
            throw new Exception("Korisnicko ime vec postoji!!!");
        }

        if (!korisnikDto.getLozinka().equals(korisnikDto.getPotvrdaLozinke())) {
            throw new Exception("Neispravna lozinka!!!");
        }

        Korisnik korisnik = new Korisnik(korisnikDto.getIme(),
                korisnikDto.getPrezime(), korisnikDto.getKorisnickoIme(), korisnikDto.getMail(), korisnikDto.getLozinka(),
                korisnikDto.getPotvrdaLozinke());



        Korisnik noviKorisnik= this.korisnikService.save(korisnik);


        KorisnikDto noviKorisnikDto = new KorisnikDto(noviKorisnik.getId(), noviKorisnik.getIme(), noviKorisnik.getPrezime(),
                noviKorisnik.getMail(), noviKorisnik.getKorisnickoIme(), noviKorisnik.getDatumRodjenja(), noviKorisnik.getProfilnaSlika(),
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

        if(korisnikDto==null)  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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

    @GetMapping(value = "/pretragapoimenu",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KorisnikDto>> getKorisnik(@RequestParam(value = "ime") String ime, @RequestParam(value = "prezime") String prezime)
    {
        List<Korisnik> korisnici = new ArrayList<Korisnik>();

        korisnici = this.korisnikService.findByImeAndPrezime(ime, prezime);

        List<KorisnikDto> korisnikDtos = new ArrayList<KorisnikDto>();

        for (Korisnik k : korisnici) {

            KorisnikDto korisnikDto = new KorisnikDto(k.getId(), k.getIme(), k.getPrezime(), k.getKorisnickoIme(), k.getMail(),
                                      k.getLozinka(), k.getDatumRodjenja(), k.getProfilnaSlika(), k.getOpis(), k.getUloga());

            korisnikDtos.add(korisnikDto);

        }
        if(korisnikDtos.isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(korisnikDtos, HttpStatus.OK);

    }

   /* @GetMapping(value = "/{}/police", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PolicaDto>> getPolicijeByKorisnikId(@PathVariable Long korisnikId) {
        Korisnik korisnik = korisnikService.findById(korisnikId);

        if (korisnik == null) {
            return ResponseEntity.notFound().build();
        }


        List<Polica> police = policaService.getPolicijeByKorisnik(korisnik);
        List<PolicaDto> policeDto = policaService.convertToDtoList(police);

        return ResponseEntity.ok(policeDto);
    }*/

    @GetMapping("/police/{korisnikId}")
    public ResponseEntity<List<KnjigaDto>> getPoliceKorisnika(@PathVariable String korisnikId) {
       // List<Knjiga> knjige = korisnikService.pregledajPoliceKorisnika(korisnikId);

        Optional<Korisnik> optionalKorisnik = korisnikService.findById(Long.valueOf(korisnikId));
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();
            Set<Polica> police = korisnik.getPolice();
            List<Knjiga> knjige = new ArrayList<>();
            List<KnjigaDto> dtos = new ArrayList<>();

            for (Polica polica : police) {
                Set<StavkaPolice> stavke = polica.getStavkaPolice();
                for (StavkaPolice stavka : stavke) {
                    Knjiga knjiga = stavka.getKnjiga();
                    knjige.add(knjiga);
                    KnjigaDto dto = new KnjigaDto(stavka.getKnjiga());
                    dtos.add(dto);
                }

            }

            return new ResponseEntity<>(dtos, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

 /*   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> napraviAutora() {




    }
*/


}
