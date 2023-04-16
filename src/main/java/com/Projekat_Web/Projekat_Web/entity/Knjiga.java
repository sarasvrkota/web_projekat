package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "knjiga")
public class Knjiga {

    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column//PRIMARNI KLJUC , u bazi se tako zove kolona
    private Long id;*/

    @Id
    @Column
    private String naslov;

    @Column(name = "naslovna_fotografija")
    private String naslovnaFotografija;

    @Column(name = "isbn")
    private String ISBN;

    @Column(name = "datum_objavljivanja")
    private Date datumObjavljivanja;

    @Column(name = "broj_strana")
    private int brojStrana;

    @Column
    private String opis;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //jedna knjiga jedan zanr
    @JoinColumn(name = "zanr", referencedColumnName = "naziv") ///ime kolone je zanr_id (strani kljuc za tabelu zanr)veza izmedju zanr i knjiga, ALIJAZ ZA ID IZ ZANRA
    // a referance je primarni kljuc u Zanr tabeli (kolona id)
    private Zanr zanr = new Zanr(); //ovo polje se povezuje sa entitetim zanr preko zanr_id

    @Column
    private double ocena;


/*
   @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;
*/
  /*  @ManyToMany(mappedBy = "knjiga")
    private Set<Autor> autor = new HashSet<>();*/

}