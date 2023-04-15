package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.time.LocalDate;

enum Uloga {CITALAC, AUTOR, ADMINISTRATOR}

@Entity
@Getter @Setter @ToString
@Table(name = "korisnik")
@Inheritance(strategy = InheritanceType.JOINED)
public class Korisnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(unique = true, nullable = false, name = "korisnicko_ime")
    private String korisnickoIme;

    @Column(unique = true, nullable = false)
    private String mail;

    @Column(nullable = false)
    private String lozinka;

    @Column(name = "datum_rodjenja")
    private LocalDate datumRodjenja;

    @Column(name = "profilna_slika")
    private String profilnaSlika;

    @Column
    private String opis;

    @Column
    private Uloga uloga;

}
