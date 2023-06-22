package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.controller.EmailRestController;
import com.Projekat_Web.Projekat_Web.dto.ZahtevZaAktivacijuNalogaAutoraDto;
import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora;
import com.Projekat_Web.Projekat_Web.repository.AutorRepository;
import com.Projekat_Web.Projekat_Web.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ZahtevZaAktivacijuNalogaAutoraService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EmailRestController emailRestController;


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


        Autor autor = autorRepository.findById(prijavljeniKorisnik.getId()).orElse(null);
        if (autorRepository.getById(zahtev.getId()).isJeAktivan()) {
            throw new RuntimeException("Autor je aktivan.");
        }
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


        zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev2);


        emailRestController.sendEmailReject(zahtev2.getEmail());


    }


    public void prihvatiZahtev(Long id, Korisnik prijavljeniKorisnik) {

        ZahtevZaAktivacijuNalogaAutora zahtev2 = new ZahtevZaAktivacijuNalogaAutora();
        Autor autor = autorRepository.findById(id).orElse(null);
        zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor);
        zahtev2.setPoruka(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getPoruka());
        zahtev2.setDatum(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getDatum());
        zahtev2.setTelefon(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getTelefon());
        zahtev2.setStatus(ZahtevZaAktivacijuNalogaAutora.Status.ODOBREN);
        zahtev2.setAutor(autor);
        autor.setJeAktivan(true);
        zahtev2.setEmail(zahtevZaAktivacijuNalogaAutoraRepository.findByAutor(autor).getEmail());

        zahtevZaAktivacijuNalogaAutoraRepository.save(zahtev2);

        autorRepository.save(autor);

        String email2 = zahtev2.getEmail();
        String lozinka = Generator.generisiLozinku();
        autor.setLozinka(lozinka);

        emailRestController.sendEmailApproved(email2, lozinka);

    }

}
