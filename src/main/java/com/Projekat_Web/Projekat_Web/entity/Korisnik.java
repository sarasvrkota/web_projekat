package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

enum Uloga {CITALAC, AUTOR, ADMINISTRATOR}

@Entity
@Getter @Setter @ToString
@Table(name = "korisnik")
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik implements Serializable {

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

}
