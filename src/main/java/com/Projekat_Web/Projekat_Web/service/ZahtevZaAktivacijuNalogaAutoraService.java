package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.controller.EmailRestController;
import com.Projekat_Web.Projekat_Web.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Polica;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora;
import com.Projekat_Web.Projekat_Web.repository.AutorRepository;
import com.Projekat_Web.Projekat_Web.repository.KorisnikRepository;
import com.Projekat_Web.Projekat_Web.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ZahtevZaAktivacijuNalogaAutoraService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EmailRestController emailRestController;

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private PolicaService policaService;


    public ZahtevZaAktivacijuNalogaAutora login(String email, String telefon) {
        ZahtevZaAktivacijuNalogaAutora zahtev = zahtevZaAktivacijuNalogaAutoraRepository.findByEmail(email);
        if(zahtev == null)
            return zahtev;
        return  null;
    }
    public ZahtevZaAktivacijuNalogaAutora getById(Long id) { return this.zahtevZaAktivacijuNalogaAutoraRepository.getById(id);}

    public ZahtevZaAktivacijuNalogaAutora save(ZahtevZaAktivacijuNalogaAutora zahtevZaAktivacijuNalogaAutora) {
        return this.zahtevZaAktivacijuNalogaAutoraRepository.save(zahtevZaAktivacijuNalogaAutora);
    }

    public List<ZahtevZaAktivacijuNalogaAutora> findAll(){
        return zahtevZaAktivacijuNalogaAutoraRepository.findAll();
    }

    public ZahtevZaAktivacijuNalogaAutoraDto findOne(Long id){
        Optional<ZahtevZaAktivacijuNalogaAutora> foundZahtev = zahtevZaAktivacijuNalogaAutoraRepository.findById(id);
        if (foundZahtev.isPresent()) {
            return new ZahtevZaAktivacijuNalogaAutoraDto(foundZahtev.get());
        }
        return null;
    }


    public void podnesiZahtevZaAktivaciju(ZahtevZaAktivacijuNalogaAutoraDto zahtev, Korisnik prijavljeniKorisnik) {


        /*Autor autor = autorRepository.findById(prijavljeniKorisnik.getId()).orElse(null);
        if (autorRepository.getById(zahtev.getId()).isJeAktivan()) {
            throw new RuntimeException("Autor je aktivan.");
        }*/
        ZahtevZaAktivacijuNalogaAutora zahtev1 = new ZahtevZaAktivacijuNalogaAutora();
        zahtev1.setEmail(zahtev.getEmail());
        zahtev1.setTelefon(zahtev.getTelefon());
        zahtev1.setPoruka(zahtev.getPoruka());
        zahtev1.setDatum(new Date());
        zahtev1.setStatus(ZahtevZaAktivacijuNalogaAutora.Status.NA_CEKANJU);
        zahtev1.setAutor(autorRepository.getById(zahtev.getId()));
        zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev1);
    }
    public ZahtevZaAktivacijuNalogaAutora findByAutor(Autor autor)
    { return this.zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor); }

    public void odbijZahtev(Long id, Korisnik prijavljeniKorisnik) {

        ZahtevZaAktivacijuNalogaAutora zahtev2 = new ZahtevZaAktivacijuNalogaAutora();
        Autor autor = autorRepository.findById(id).orElse(null);
        zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor);
        zahtev2.setPoruka(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getPoruka());
        zahtev2.setDatum(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getDatum());
        zahtev2.setTelefon(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getTelefon());
        zahtev2.setStatus(ZahtevZaAktivacijuNalogaAutora.Status.ODBIJEN);
        zahtev2.setAutor(autor);
        zahtev2.setEmail(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getEmail());


        zahtevZaAktivacijuNalogaAutoraRepository.delete(zahtev2);


        emailRestController.sendEmailReject(zahtev2.getEmail());


    }


    public void prihvatiZahtev(Long id, Korisnik prijavljeniKorisnik) {

        ZahtevZaAktivacijuNalogaAutora zahtev2 = new ZahtevZaAktivacijuNalogaAutora();
        Autor a = autorRepository.findById(id).orElse(null);
        zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(a);
        zahtev2.setPoruka(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(a).getPoruka());
        zahtev2.setDatum(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(a).getDatum());
        zahtev2.setTelefon(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(a).getTelefon());
        zahtev2.setStatus(ZahtevZaAktivacijuNalogaAutora.Status.ODOBREN);
        zahtev2.setAutor(a);
        a.setJeAktivan(true);
        zahtev2.setEmail(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(a).getEmail());
        zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev2);
        autorRepository.save(a);
        Korisnik korisnik = korisnikRepository.getById(a.getId());
        Polica wantToRead = new Polica();
        wantToRead.setNaziv("Want to Read");
        wantToRead.setPrimarna(true);

        Polica currentlyReading = new Polica();
        currentlyReading.setNaziv("Currently Reading");
        currentlyReading.setPrimarna(true);

        Polica read = new Polica();
        read.setNaziv("Read");
        read.setPrimarna(true);

        korisnik.getPolice().add(wantToRead);
        korisnik.getPolice().add(currentlyReading);
        korisnik.getPolice().add(read);

        korisnikRepository.save(korisnik);





        String email2 = zahtev2.getEmail();
        String lozinka = Generator.generisiLozinku();
        a.setLozinka(lozinka);

        emailRestController.sendEmailApproved(email2, lozinka);

    }

}
