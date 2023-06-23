package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.dto.PolicaDto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.repository.*;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class KorisnikService {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private PolicaService policaService;
    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    @Autowired
    private PolicaRepository policaRepository;

    public Korisnik save(Korisnik korisnik)
    {
        return this.korisnikRepository.save(korisnik);
    }
    public Korisnik findByMail(String mail) { return this.korisnikRepository.findByMail(mail); }
    public Korisnik findByKorisnickoIme(String korisnickoIme)
    {
        return this.korisnikRepository.findByKorisnickoIme(korisnickoIme);
    }
    public Optional<Korisnik> findById(Long id) {
        return this.korisnikRepository.findById(id);
    }
    public Korisnik getById(Long id) { return this.korisnikRepository.getById(id);}

   /* public Korisnik login(String mail, String lozinka) {
        Korisnik korisnik = korisnikRepository.findByMail(mail);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        return  korisnik;
    }*/

    /*public Korisnik findOne(Long id){
        Optional<Korisnik> foundEmployee = korisnikRepository.findById(id);
        if (foundEmployee.isPresent())
            return foundEmployee.get();

        return null;
    }*/

    public List<Korisnik> findByImeAndPrezime(String ime, String prezime) {
        return korisnikRepository.findByImeAndPrezime(ime, prezime);
    }


        public Korisnik findOne(Long id){
            Optional<Korisnik> foundKorisnik = korisnikRepository.findById(id);
            if (foundKorisnik.isPresent()) {
                return foundKorisnik.get();
            }
            return null;
        }

    public void  dodajPrimarnePoliceKorisniku(Korisnik korisnik) {
        if (korisnik.getUloga() == Korisnik.Uloga.CITALAC || korisnik.getUloga() == Korisnik.Uloga.AUTOR) {
            Polica wantToRead = new Polica("Want to Read", true, new HashSet<>());
            Polica currentlyReading = new Polica("Currently Reading", true, new HashSet<>());
            Polica read = new Polica("Read", true, new HashSet<>());

            korisnik.getPolice().add(wantToRead);
            policaRepository.save(wantToRead);
            korisnik.getPolice().add(currentlyReading);
            policaRepository.save(currentlyReading);
            korisnik.getPolice().add(read);
            policaRepository.save(read);
        }
    }
    public Korisnik login(String mail, String lozinka) {
        Korisnik korisnik = korisnikRepository.findByMail(mail);
        if(korisnik == null || !korisnik.getLozinka().equals(lozinka))
            return null;
        dodajPrimarnePoliceKorisniku(korisnik);
        korisnikRepository.save(korisnik);

        return  korisnik;
    }

    public List<Knjiga> pregledajPoliceKorisnika(String korisnikId) {
        // Implementirajte logiku za pronalaženje knjiga na policama korisnika

        Optional<Korisnik> optionalKorisnik = korisnikRepository.findById(Long.valueOf(korisnikId));
        if (optionalKorisnik.isPresent()) {
            Korisnik korisnik = optionalKorisnik.get();
            Set<Polica> police = korisnik.getPolice();
            List<Knjiga> knjige = new ArrayList<>();

            for (Polica polica : police) {
                Set<StavkaPolice> stavke = polica.getStavkaPolice();
                for (StavkaPolice stavka : stavke) {
                    Knjiga knjiga = stavka.getKnjiga();
                    knjige.add(knjiga);
                }
            }

            return knjige;
        }

        return Collections.emptyList();
    }

}
