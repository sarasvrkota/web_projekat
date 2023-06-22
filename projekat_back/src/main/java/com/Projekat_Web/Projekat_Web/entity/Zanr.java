package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString
@Table(name = "zanr")
public class Zanr {

   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //PRIMARNI KLJUC KOJI SE KORISTI KAO VEZA*/
    @Id
    @Column(name = "naziv", nullable = false, unique = true)
    private String naziv;

    public Zanr(String naziv) {
        this.naziv = naziv;
    }

    public Zanr() {}
}

