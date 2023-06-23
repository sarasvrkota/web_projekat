package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.AutorDto;
import com.Projekat_Web.Projekat_Web.dto.KnjigaDto;
import com.Projekat_Web.Projekat_Web.dto.KorisnikDto;
import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.service.AutorService;
import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import com.Projekat_Web.Projekat_Web.service.PolicaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private PolicaService policaService;


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

        Knjiga knjiga = this.knjigaService.getByNaslov(naslovKnjige);
        knjiga.setNaslov(knjigaDto.getNaslov());
        knjiga.setNaslovnaFotografija(knjigaDto.getNaslovnaFotografija());
        knjiga.setISBN(knjigaDto.getISBN());
        knjiga.setDatumObjavljivanja(knjigaDto.getDatumObjavljivanja());
        knjiga.setBrojStrana(knjigaDto.getBrojStrana());
        knjiga.setOpis(knjigaDto.getOpis());
        knjiga.setZanr(knjigaDto.getZanr());
        knjiga.setOcena(knjigaDto.getOcena());

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


    @PutMapping("/azuriraj-autora/{id}")
    public ResponseEntity<?> azurirajProfilAutora(@PathVariable Long id, @RequestBody AutorDto autorDto,  HttpSession session) {
        Autor prijavljeniKorisnik = (Autor) session.getAttribute("korisnik");
        Autor autor = this.autorService.getById(id);
        if (prijavljeniKorisnik != null && prijavljeniKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {
            autorService.azurirajProfilAutora(id, autorDto);

            return ResponseEntity.ok("Uspesno azuriran autor!");
        }
        return ResponseEntity.ok("Autor nije azuriran!");

    }


   /* @PostMapping(value = "/registruj-autora/{id}")
    public ResponseEntity<AutorDto> azurirajProfilAutora(@PathVariable Long id, HttpSession session) throws Exception {

        Korisnik optKorisnik = (Korisnik)session.getAttribute("korisnik");
        Autor autor1 = this.autorService.getById(id);
        AutorDto autorDto = new AutorDto(autor1);
    if(optKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR)) {


        Autor autor = new Autor(autorDto.getIme(),
                autorDto.getPrezime(), autorDto.getKorisnickoIme(), autorDto.getMail(), autorDto.getLozinka());

        //dodajPrimarnePolice(session);


        Set<Polica> policee = new HashSet<>();
        Polica polica1 = new Polica("Want to Read", true, new HashSet<>());
        //korisnikk.getPolice().add(polica1);
        policee.add(polica1);
        policaService.save(polica1);


        Polica polica2 = new Polica("Currently Reading", true, new HashSet<>());
        policaService.save(polica2);
        policee.add(polica2);
        //korisnikk.getPolice().add(polica2);

        Polica polica3 = new Polica("Read", true, new HashSet<>());
        policee.add(polica3);
        policaService.save(polica3);
        //korisnikk.getPolice().add(polica3);

        autor.setPolice(policee);
        korisnikService.save(autor);


        Autor noviAutor = this.autorService.save(autor);


        AutorDto noviAutorDto = new AutorDto(noviAutor.getIme(), noviAutor.getPrezime(),
                noviAutor.getMail(), noviAutor.getKorisnickoIme(), noviAutor.getDatumRodjenja(), noviAutor.getProfilnaSlika(),
                noviAutor.getOpis(), noviAutor.getUloga(), true);
        // videti sa sarom sta da prosledjujemo korisniku

        return new ResponseEntity<>(noviAutorDto, HttpStatus.CREATED);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }*/


    @PostMapping(value = "/registruj-autora/{id}")
    public ResponseEntity<?> azurirajProfilAutora(@PathVariable Long id, HttpSession session) throws Exception {
        Korisnik optKorisnik = (Korisnik)session.getAttribute("korisnik");
        Autor autor1 = this.autorService.getById(id);
        if(optKorisnik.getUloga().equals(Korisnik.Uloga.ADMINISTRATOR) && autor1.isJeAktivan() == false) {

            autor1.setJeAktivan(true);

            Set<Polica> policee = new HashSet<>();
            Polica polica1 = new Polica("Want to Read", true, new HashSet<>());
            //korisnikk.getPolice().add(polica1);
            policee.add(polica1);
            policaService.save(polica1);


            Polica polica2 = new Polica("Currently Reading", true, new HashSet<>());
            policaService.save(polica2);
            policee.add(polica2);
            //korisnikk.getPolice().add(polica2);

            Polica polica3 = new Polica("Read", true, new HashSet<>());
            policee.add(polica3);
            policaService.save(polica3);
            //korisnikk.getPolice().add(polica3);

            autor1.setPolice(policee);
            korisnikService.save(autor1);


            Autor noviAutor = this.autorService.save(autor1);


            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }





}
