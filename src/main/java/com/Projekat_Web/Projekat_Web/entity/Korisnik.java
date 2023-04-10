package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Korisnik {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // da li samo id?

    @Column
    private String ime;

    @Column
    private String prezime;

    @Column
    private String datum_rodjenja;
//?????
    @Column
    private String opis;

    @Column
    private String prezime;
//sta je za ulogu





}
