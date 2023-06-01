package com.Projekat_Web.Projekat_Web.dto;


import com.Projekat_Web.Projekat_Web.entity.Korisnik.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;

import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@ToString

public class KorisnikDto {

    private Long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String mail;
   // @JsonIgnore
    private String lozinka;
    //@JsonIgnore
    private String potvrdaLozinke;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private Uloga uloga;


    public KorisnikDto() {
    }

    //konstruktor sa svim
    public KorisnikDto(Long id, String ime, String prezime, String korisnickoIme,
                       String mail, String lozinka, LocalDate datumRodjenja, String profilnaSlika, String opis, Uloga uloga)
    {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.lozinka = lozinka;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
    }

    //konstruktor bez username i lozinke
     public KorisnikDto(Long id, String ime, String prezime, String mail, String korisnickoIme, LocalDate datumRodjenja,
                       String profilnaSlika, String opis, Uloga uloga)
    {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.mail = mail;
        this.korisnickoIme = korisnickoIme;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
    }

    /*KorisnikDto(noviKorisnik.getId(), noviKorisnik.getIme(), noviKorisnik.getPrezime(),
                                        noviKorisnik.getKorisnickoIme(),*/

 /*   public KorisnikDto(Long id, String ime, String prezime, String mail, String korisnickoIme, LocalDate datumRodjenja,
                       String profilnaSlika, String opis, Uloga uloga) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
    }*/

  /*  public KorisnikDto(Long id, String ime, String prezime, String korisnickoIme, String mail) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
    }*/

  /*  public KorisnikDto(Long id, String ime, String prezime, String mail, String korisnickoIme, LocalDate datumRodjenja,
                       String profilnaSlika, String opis, Uloga uloga) {

        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.mail = mail;
        this.korisnickoIme = korisnickoIme;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
    }*/



    /*   public KorisnikDto(Long id, String ime, String prezime, String korisnickoIme, String mail, String lozinka,
                       String potvrdaLozinke, LocalDate datumRodjenja, String profilnaSlika, String opis, Uloga uloga) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.lozinka = lozinka;
        this.potvrdaLozinke = potvrdaLozinke;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
    }*/

    public KorisnikDto(String ime, String prezime, String korisnickoIme, String mail, String lozinka, LocalDate datumRodjenja,
                       String profilnaSlika, String opis)
    {

        this.ime = ime;
        this.prezime = prezime;
        this.mail = mail;
        this.lozinka = lozinka;
        this.korisnickoIme = korisnickoIme;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;

    }
}
