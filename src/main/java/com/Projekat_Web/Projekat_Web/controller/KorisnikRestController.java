package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.KorisnikDto;
import com.Projekat_Web.Projekat_Web.dto.LoginDto;
import com.Projekat_Web.Projekat_Web.dto.PolicaDto;
import com.Projekat_Web.Projekat_Web.entity.*;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import com.Projekat_Web.Projekat_Web.service.PolicaService;
import com.Projekat_Web.Projekat_Web.service.StavkaPoliceService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/korisnici")
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;
    @Autowired
    private PolicaService policaService;
    @Autowired
    private KnjigaService knjigaService;
    @Autowired
    private StavkaPoliceService stavkaPoliceServiceService;


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
        dodajPrimarnePolice(ulogovaniKorisnik, session);

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


    @PostMapping("/primarne-police")
    public ResponseEntity<Void> dodajPrimarnePolice(Korisnik korisnik, HttpSession session){
        Optional<Korisnik> optKorisnik = this.korisnikService.findById(korisnik.getId());
        Korisnik korisnikk = optKorisnik.get();
        if(korisnikk.getUloga() != Korisnik.Uloga.CITALAC){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        Polica polica1 = new Polica("Want to Read", true);
        korisnikk.getPolice().add(polica1);
        policaService.save(polica1);

        Polica polica2 = new Polica("Currently Reading", true);
        policaService.save(polica2);
        korisnikk.getPolice().add(polica2);

        Polica polica3 = new Polica("Read", true);
        policaService.save(polica3);
        korisnikk.getPolice().add(polica3);
        korisnikService.save(korisnikk);

        return  ResponseEntity.noContent().build();

    }
//    Korisnik ima mogućnost dodavanja drugih polica, koje kasnije može i obrisati.
//    Korisnik može da napravi novu policu samo ukoliko već nema policu sa
//    izabranim imenom.

    @PostMapping("/dodavanje-polica")
    public ResponseEntity<String> dodajPolicu(@RequestParam(value = "naziv") String naziv, HttpSession session){


        if(naziv.equals("")){
            return new ResponseEntity<>("Naziv nije validan!", HttpStatus.BAD_REQUEST);
        }
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        /*if (prijavljeniKorisnik == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/
        for(Polica p : prijavljeniKorisnik.getPolice()){
            if(naziv.equals(p.getNaziv())){
                return new ResponseEntity<>( "Polica sa ovim nazivom vec postoji!",HttpStatus.FORBIDDEN);
            }
        }
        Polica polica = new Polica(naziv, false);
        prijavljeniKorisnik.getPolice().add(polica);
        korisnikService.save(prijavljeniKorisnik);


        return new ResponseEntity<String>("kreirano", HttpStatus.CREATED);
    }

/*
    @PostMapping("/dodaj-knjigu-na-policu")
    public ResponseEntity<String> dodajKnjiguNaPolicu(@RequestParam(value = "policaNaziv") String policaNaziv, @RequestParam(value = "knjigaNaslov") String knjigaNaslov, HttpSession session) {
        // Provera validnosti
        if (policaNaziv.equals("") || knjigaNaslov.equals("")) {
            return new ResponseEntity<>("Nije dobar unos!", HttpStatus.BAD_REQUEST);
        }

        // Da li je korisnik prijavljen
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Pronalaženje knjige koju želimo da dodamo na policu
        Knjiga knjiga = knjigaService.findByNaslov(knjigaNaslov);
        if (knjiga == null) {
            return new ResponseEntity<>("Knjiga ne postoji!", HttpStatus.NOT_FOUND);
        }

        // Provera da li korisnik ima odabranu policu medju svojim primarnim policama
        Polica targetPrimarnaPolica = null;
        for (Polica polica : prijavljeniKorisnik.getPolice()) {
            if (polica.getNaziv().equals(policaNaziv) && polica.isPrimarna()) {
                targetPrimarnaPolica = polica;
                break;
            }
        }

        // Provera da li korisnik ima odabranu policu medju policama koje je sam napravio
        Polica targetSopstvenaPolica = null;
        for (Polica polica : prijavljeniKorisnik.getPolice()) {
            if (polica.getNaziv().equals(policaNaziv) && !polica.isPrimarna()) {
                targetSopstvenaPolica = polica;
                break;
            }
        }

        if (targetPrimarnaPolica == null && targetSopstvenaPolica == null) {
            return new ResponseEntity<>("Polica ne postoji ili nije validna!", HttpStatus.NOT_FOUND);
        }

        // Uklanjanje knjige sa svih polica korisnika
        for (Polica polica : prijavljeniKorisnik.getPolice()) {
            polica.getStavkaPolice().removeIf(stavka -> stavka.getKnjiga().equals(knjiga));
            policaService.save(polica);
        }

        // Dodavanje knjige na ciljanu policu
        if (targetPrimarnaPolica != null) {
            StavkaPolice novaStavka = new StavkaPolice(knjiga, null);
            targetPrimarnaPolica.getStavkaPolice().add(novaStavka);
            policaService.save(targetPrimarnaPolica);
        } else if (targetSopstvenaPolica != null) {
            StavkaPolice novaStavka = new StavkaPolice(knjiga, null);
            targetSopstvenaPolica.getStavkaPolice().add(novaStavka);
            policaService.save(targetSopstvenaPolica);
        }

        return new ResponseEntity<>("Knjiga uspešno dodata na policu!", HttpStatus.OK);
    }


    @PostMapping("/azuriraj-profil")
    public ResponseEntity<String> azurirajProfil(@RequestBody KorisnikDto korisnikDTO, @RequestBody LoginDto loginDTO, HttpSession session) {
        // Provera da li je korisnik prijavljen
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Provera da li je uneta trenutna lozinka ispravna
        String trenutnaLozinka = loginDTO.getLozinka();
        if (!trenutnaLozinka.equals(prijavljeniKorisnik.getLozinka())) {
            return new ResponseEntity<>("Trenutna lozinka nije ispravna!", HttpStatus.BAD_REQUEST);
        }

        // Ažuriranje podataka korisnika
        prijavljeniKorisnik.setIme(korisnikDTO.getIme());
        prijavljeniKorisnik.setPrezime(korisnikDTO.getPrezime());
        prijavljeniKorisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
        prijavljeniKorisnik.setMail(korisnikDTO.getMail());
        prijavljeniKorisnik.setDatumRodjenja(korisnikDTO.getDatumRodjenja());
        prijavljeniKorisnik.setProfilnaSlika(korisnikDTO.getProfilnaSlika());
        prijavljeniKorisnik.setOpis(korisnikDTO.getOpis());

        // Provera da li je uneta nova lozinka i ažuriranje lozinke ako je uneta
        String novaLozinka = korisnikDTO.getLozinka();
        if (!novaLozinka.isEmpty()) {
            prijavljeniKorisnik.setLozinka(novaLozinka);
        }

        // Sačuvajte izmenjenog korisnika u bazi podataka
        korisnikService.save(prijavljeniKorisnik);

        return new ResponseEntity<>("Profil uspešno ažuriran!", HttpStatus.OK);

    }


    @PostMapping("/dodaj-knjigu-na-policu-read")
    public ResponseEntity<String> dodajKnjiguNaPolicuRead(@RequestParam(value = "policaNaziv") String naziv, @RequestParam(value = "knjigaNaslov") String knjigaNaslov, HttpSession session) {
        // Provera da li su podaci validni
        if (naziv.isEmpty() || knjigaNaslov.isEmpty()) {
            return new ResponseEntity<>("Nije dobar unos!", HttpStatus.BAD_REQUEST);
        }

        // Provera da li je korisnik prijavljen
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        if (prijavljeniKorisnik == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Pronalaženje knjige koju želimo da dodamo na policu
        Knjiga knjiga = knjigaService.findByNaslov(knjigaNaslov);
        if (knjiga == null) {
            return new ResponseEntity<>("Knjiga ne postoji!", HttpStatus.NOT_FOUND);
        }

        // Pronalaženje "Read" police korisnika
        Polica readPolica = null;
        for (Polica polica : prijavljeniKorisnik.getPolice()) {
            if (polica.getNaziv().equals("Read")) {
                readPolica = polica;
                break;
            }
        }

        if (readPolica == null) {
            return new ResponseEntity<>("Read polica ne postoji!", HttpStatus.NOT_FOUND);
        }

        // Dodavanje knjige na "Read" policu
        StavkaPolice novaStavka = new StavkaPolice(knjiga, null);
        readPolica.getStavkaPolice().add(novaStavka);
        policaService.save(readPolica);

        // Dodavanje recenzije na knjigu
        Recenzija novaRecenzija = new Recenzija();
        novaRecenzija.setOcena(0); // Postavite željenu ocenu
        novaRecenzija.setTekst(""); // Postavite željeni tekst recenzije
        novaRecenzija.setDatumRecenzije(new Date());
        novaRecenzija.setKorisnik(prijavljeniKorisnik);
        novaStavka.setRecenzija(novaRecenzija); // Postavite recenziju na stavku

        return new ResponseEntity<>("Knjiga uspesno dodata na policu!", HttpStatus.OK);
    }*/
}








