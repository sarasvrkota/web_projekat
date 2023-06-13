package com.Projekat_Web.Projekat_Web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.*;
//import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter @ToString
@Table(name = "recenzija")
public class Recenzija implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private double ocena;

    @Column
    private String tekst;

    @Column(name = "datum_recenzije")
    private Date datumRecenzije;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "korisnik_id", referencedColumnName = "korisnikk_id")
    private Korisnik korisnik;

    public Recenzija(double ocena, String tekst, Date datumRecenzije, Korisnik korisnik) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
        this.korisnik = korisnik;
    }

    public Recenzija() {}
}
