package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.dto.*;
import com.Projekat_Web.Projekat_Web.entity.*;
import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import com.Projekat_Web.Projekat_Web.service.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    private RecenzijaService recenzijaService;

    @Autowired
    private StavkaPoliceService stavkaPoliceService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraService zahtevZaAktivacijuNalogaAutoraService;


    @GetMapping("/pocetna-stranica")
    public String welcome(){
        return "Dobrodosli na stranicu!";
    }


    @PostMapping(value = "/registracija",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KorisnikDto> napraviKorisnika(@RequestBody KorisnikDto korisnikDto, HttpSession session) throws Exception {

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

            korisnik.setPolice(policee);
            korisnikService.save(korisnik);



        Korisnik noviKorisnik= this.korisnikService.save(korisnik);


        KorisnikDto noviKorisnikDto = new KorisnikDto(noviKorisnik.getId(), noviKorisnik.getIme(), noviKorisnik.getPrezime(),
                noviKorisnik.getMail(), noviKorisnik.getKorisnickoIme(), noviKorisnik.getDatumRodjenja(), noviKorisnik.getProfilnaSlika(),
                noviKorisnik.getOpis(), noviKorisnik.getUloga());
        // videti sa sarom sta da prosledjujemo korisniku

        return new ResponseEntity<>(noviKorisnikDto, HttpStatus.CREATED);

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
        //dodajPrimarnePolice(session);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/getUserRole")
    public ResponseEntity<String> getUserRole(HttpSession session) {
        Korisnik korisnik = (Korisnik) session.getAttribute("korisnik");
        if (korisnik == null) {
            return new ResponseEntity<>("User not logged in", HttpStatus.UNAUTHORIZED);
        }

        // Dobavljanje uloge korisnika
        String userRole = korisnik.getUloga().toString(); // Pretpostavka da uloga korisnika ima toString() metodu

        return ResponseEntity.ok(userRole);
    }

    @PostMapping("/logout")
    public ResponseEntity Logout(HttpSession session){
        Korisnik ulogovaniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (ulogovaniKorisnik == null)
            return new ResponseEntity("Forbidden", HttpStatus.FORBIDDEN);

        session.invalidate();
        return new ResponseEntity("Successfully logged out", HttpStatus.OK);
    }

    @GetMapping(value = "/pretraga-po-imenu",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KorisnikDto>> getKorisnik(@RequestParam(value = "ime") String ime,
                                                         @RequestParam(value = "prezime") String prezime)
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

    @GetMapping(value= "/prikaz-profila/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<Void> dodajPrimarnePolice(HttpSession session){
        Korisnik optKorisnik = (Korisnik)session.getAttribute("korisnik");
        if (optKorisnik ==  null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Korisnik korisnikk = optKorisnik;
        //this.korisnikService.save(korisnikk);

       /* if(korisnikk.getId() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);*/

        if(korisnikk.getUloga() == Korisnik.Uloga.CITALAC || korisnikk.getUloga() == Korisnik.Uloga.AUTOR) {

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

            korisnikk.setPolice(policee);
            korisnikService.save(korisnikk);



            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
//    Korisnik ima mogućnost dodavanja drugih polica, koje kasnije može i obrisati.
//    Korisnik može da napravi novu policu samo ukoliko već nema policu sa
//    izabranim imenom.

    @PostMapping("/dodavanje-polica")
    public ResponseEntity<String> dodajPolicu(@RequestParam(value = "naziv") String naziv, HttpSession session){

        if(naziv.equals("")){
            return new ResponseEntity<>("Naziv nije validan!", HttpStatus.BAD_REQUEST);
        }
        Korisnik korisnikk = (Korisnik) session.getAttribute("korisnik");
        Korisnik prijavljeniKorisnik = this.korisnikService.getById(korisnikk.getId());
        Set<Polica> p1 = new HashSet<>();
        if(prijavljeniKorisnik.getUloga() == Korisnik.Uloga.CITALAC || prijavljeniKorisnik.getUloga() == Korisnik.Uloga.AUTOR) {


        /*if (prijavljeniKorisnik == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }*/
            for (Polica p : prijavljeniKorisnik.getPolice()) {
                if (naziv.equals(p.getNaziv())) {
                    return new ResponseEntity<>("Polica sa ovim nazivom vec postoji!", HttpStatus.FORBIDDEN);
                }
            }
            Polica polica = new Polica(naziv, false, new HashSet<>());
            policaService.save(polica);
           // prijavljeniKorisnik.getPolice().add(polica);
            p1 = prijavljeniKorisnik.getPolice();
            p1.add(polica);
            prijavljeniKorisnik.setPolice(p1);
            policaService.save(polica);
            korisnikService.save(prijavljeniKorisnik);

            return new ResponseEntity<String>("kreirano", HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/police/{policaId}")
    public ResponseEntity<String> obrisiPolicu(@PathVariable Long policaId, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        Polica polica = policaService.getById(policaId);
        //System.out.println(prijavljeniKorisnik.getId());

        if(prijavljeniKorisnik.getUloga() == Korisnik.Uloga.CITALAC || prijavljeniKorisnik.getUloga() == Korisnik.Uloga.AUTOR) {


            if (polica == null) {
                return new ResponseEntity<>("Policu sa datim ID-om nije moguće pronaći", HttpStatus.NOT_FOUND);
            }

            if (polica.isPrimarna()) {
                return new ResponseEntity<>("Ne može se obrisati primarna polica!", HttpStatus.FORBIDDEN);
            }

            Korisnik korisnikIzBaze = korisnikService.getById(prijavljeniKorisnik.getId());
            korisnikIzBaze.getPolice().remove(polica);
            korisnikService.save(korisnikIzBaze);
            policaService.delete(polica);


            return new ResponseEntity<>("Polica je uspešno obrisana", HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

   /* @DeleteMapping("/police/{policaId}")
    public ResponseEntity<String> obrisiPolicu(@PathVariable Long policaId, HttpSession session)
    {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");
        Polica polica = policaService.findById(policaId);
        if (polica == null) {
            return new ResponseEntity<>("Policu sa datim ID-om nije moguće pronaći", HttpStatus.NOT_FOUND);
        }

        if(polica.isPrimarna()) {
            return new ResponseEntity<>("Ne moze se obrisati primarna polica!", HttpStatus.FORBIDDEN);
        }
        Set<Polica> tmp = new HashSet<>();
        for(Polica p : prijavljeniKorisnik.getPolice()) {
            if(!p.getId().equals(polica.getId())) {
               tmp.add(p);
            } else {
                this.policaService.delete(p);
            }
        }

        prijavljeniKorisnik.setPolice(tmp);
        this.korisnikService.save(prijavljeniKorisnik);
      *//*  proci kroz police
            obrisati policu
                    ponovo setovati korisniku listu
            save korisnik*//*
*//*
        // Uklanjanje veze između korisnika i polica
        prijavljeniKorisnik.getPolice().remove(polica);
        //this.policaService.delete(id, prijavljeniKorisnik);
        korisnikService.save(prijavljeniKorisnik);

        // Brisanje polica
        policaService.delete(polica);*//*

        return new ResponseEntity<>("Policu je uspešno obrisana", HttpStatus.OK);

    }*/

    @PostMapping("/dodaj-knjigu-na-policu")
    public ResponseEntity<String> dodajKnjiguNaPolicu(@RequestParam(value = "policaId") Long policaId,
                                                      @RequestParam(value = "knjigaNaslov") String knjigaNaslov,
                                                      HttpSession session) throws ChangeSetPersister.NotFoundException {
        Korisnik loggedKorisnik = (Korisnik) session.getAttribute("korisnik");
        if(loggedKorisnik.getUloga() == Korisnik.Uloga.CITALAC || loggedKorisnik.getUloga() == Korisnik.Uloga.AUTOR) {

            Polica polica = this.policaService.getById(policaId);
            Knjiga knjiga = this.knjigaService.getByNaslov(knjigaNaslov);
            if(polica.isPrimarna()) {
                if(knjigaService.findKnjigaOnPrimarna(loggedKorisnik.getId(), knjiga.getNaslov())) {
                    return new ResponseEntity<>("Knjiga vec postoji na primarnoj", HttpStatus.BAD_REQUEST);
                } else {
                    if(polica.getNaziv().equals("Read")) {
                        policaService.addKnjigaOnPolica(policaId, knjiga.getNaslov());
                        return new ResponseEntity<>("Knjiga je dodata na Read policu", HttpStatus.OK);
                    } else {
                        policaService.addKnjigaOnPolica(policaId, knjiga.getNaslov());
                        return new ResponseEntity<>("Knjiga je dodata na primarnu policu", HttpStatus.OK);
                    }
                }
            } else {
                if(knjigaService.findKnjigaOnPrimarna(loggedKorisnik.getId(), knjiga.getNaslov())) {
                    policaService.addKnjigaOnPolica(policaId, knjiga.getNaslov());
                    return new ResponseEntity<>("Knjiga je dodata na policu koja nije primarna", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Knjiga se prvo mora staviti na jednu od primarnih polica", HttpStatus.BAD_REQUEST);
                }
            }

        }

        return new ResponseEntity<>("Vi niste taj korisnik", HttpStatus.UNAUTHORIZED);


    }



    @PostMapping("/dodavanje-recenzije/{naslov}")
    public ResponseEntity<String> dodajRecenziju(@PathVariable String naslov, @RequestBody RecenzijaDto novaRecenzijaDTO, HttpSession session) {
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if (prijavljeniKorisnik == null) {
            return new ResponseEntity<>("Korisnik nije prijavljen", HttpStatus.UNAUTHORIZED);
        }

        Knjiga knjiga = this.knjigaService.findByNaslov(naslov);
        Korisnik korisnik = this.korisnikService.getById(prijavljeniKorisnik.getId());

        StavkaPolice targetStavka = null;

        for (Polica polica : korisnik.getPolice()) {
            for (StavkaPolice stavka : polica.getStavkaPolice()) {
                if (stavka.getKnjiga().equals(knjiga)) {
                    targetStavka = stavka;
                    break;
                }
            }
        }

        if (targetStavka == null) {
            return new ResponseEntity<>("Stavka polica sa datom knjigom nije pronađena", HttpStatus.NOT_FOUND);
        }

        Recenzija novaRecenzija = new Recenzija();
        novaRecenzija.setOcena(novaRecenzijaDTO.getOcena());
        novaRecenzija.setTekst(novaRecenzijaDTO.getTekst());
        novaRecenzija.setDatumRecenzije(new Date());
        recenzijaService.save(novaRecenzija);
        novaRecenzija.setKorisnik(korisnik);

        targetStavka.setRecenzija(novaRecenzija);
        stavkaPoliceService.save(targetStavka);

        return new ResponseEntity<>("Uspesno dodata recenzija!", HttpStatus.OK);
    }
    @PostMapping("/azuriraj-profil")
    public ResponseEntity<String> azurirajProfil(@RequestBody KorisnikDto korisnikDTO, HttpSession session) {
        // Provera da li je korisnik prijavljen
        Korisnik prijavljeniKorisnik = (Korisnik) session.getAttribute("korisnik");

        if(prijavljeniKorisnik.getUloga() == Korisnik.Uloga.CITALAC || prijavljeniKorisnik.getUloga() == Korisnik.Uloga.AUTOR) {


            if (prijavljeniKorisnik == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            LoginDto l = new LoginDto(prijavljeniKorisnik.getMail(), prijavljeniKorisnik.getLozinka());
            potvrdiLozinku(l, session);
            // Provera da li je uneta trenutna lozinka ispravna
     /*   String trenutnaLozinka = loginDTO.getLozinka();
        if (!trenutnaLozinka.equals(prijavljeniKorisnik.getLozinka())) {
            return new ResponseEntity<>("Trenutna lozinka nije ispravna!", HttpStatus.BAD_REQUEST);
        }*/

            // Ažuriranje podataka korisnika
            prijavljeniKorisnik.setIme(korisnikDTO.getIme());
            prijavljeniKorisnik.setPrezime(korisnikDTO.getPrezime());
            prijavljeniKorisnik.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
            prijavljeniKorisnik.setMail(korisnikDTO.getMail());
            prijavljeniKorisnik.setLozinka(korisnikDTO.getLozinka());
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
        return new ResponseEntity(HttpStatus.FORBIDDEN);

    }

    @PostMapping("/potvrda-lozinke")
    public ResponseEntity<String> potvrdiLozinku(@RequestBody LoginDto loginDTO,  HttpSession session) {
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
        return new ResponseEntity<>("Ispravna lozinka!", HttpStatus.OK);

    }






}

