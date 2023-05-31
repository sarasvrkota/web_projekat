package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "korisnik")
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik implements Serializable {


    public void setPolica(Polica polica) {
    }

    public enum Uloga {CITALAC, AUTOR, ADMINISTRATOR}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "korisnikk_id")
    protected Long id;

    @Column(nullable = false)
    protected String ime;

    @Column(nullable = false)
    protected String prezime;

    @Column(unique = true, nullable = false, name = "korisnicko_ime")
    protected String korisnickoIme;

    @Column(unique = true, nullable = false)
    protected String mail;

    @Column(nullable = false)
    protected String lozinka;

    @Column(name = "datum_rodjenja")
    protected LocalDate datumRodjenja;

    @Column(name = "profilna_slika")
    protected String profilnaSlika;

    @Column
    protected String opis;

    @Enumerated(EnumType.STRING)
    @Column
    protected Uloga uloga;

    @OneToMany(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    protected Set<Polica> police = new HashSet<>();

    protected String potvrdaLozinke;

    public Korisnik(String ime, String prezime, String korisnickoIme, String mail,
                    String lozinka, LocalDate datumRodjenja, String profilnaSlika, String opis,
                    Uloga uloga)
    {
        this.ime = ime;
        this. prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.lozinka = lozinka;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
    }
    public Korisnik() {}

   /* public Korisnik(String ime, String prezime, String mail, LocalDate datumRodjenja, String profilnaSlika,
                    String opis, String uloga) {
    }*/

    public Korisnik(Long id, String ime, String prezime, String korisnickoIme, String mail, String lozinka,
                    LocalDate datumRodjenja, String profilnaSlika, String opis, Uloga uloga) {
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

 /*   public Korisnik( String ime, String prezime, String korisnickoIme, String mail, String lozinka,
                    String potvrdaLozinke, LocalDate datumRodjenja, String profilnaSlika, String opis, Uloga uloga) {
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

    public Korisnik(String ime, String prezime, String korisnickoIme, String mail, String lozinka, String potvrdaLozinke) {

        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.lozinka = lozinka;
        this.potvrdaLozinke = potvrdaLozinke;


    }
}
