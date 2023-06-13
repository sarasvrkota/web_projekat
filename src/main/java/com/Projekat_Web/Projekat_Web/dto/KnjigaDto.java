package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Zanr;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
public class KnjigaDto {

    private String naslov;
    private String naslovnaFotografija;
    private String ISBN;
    private Date datumObjavljivanja;
    private int brojStrana;
    private String opis;
    private Zanr zanr;
    private double ocena;

    public KnjigaDto(String naslov, String naslovnaFotografija, String ISBN,
                     Date datumObjavljivanja, int brojStrana, String opis, Zanr zanr,
                     double ocena)
    {
        this.naslov = naslov;
        this.naslovnaFotografija = naslovnaFotografija;
        this.ISBN = ISBN;
        this.datumObjavljivanja = datumObjavljivanja;
        this.brojStrana = brojStrana;
        this.opis = opis;
        this.zanr = zanr;
        this.ocena = ocena;
    }

    public KnjigaDto(Knjiga knjiga) {

        this.naslov = knjiga.getNaslov();
        this.naslovnaFotografija = knjiga.getNaslovnaFotografija();
        this.ISBN = knjiga.getISBN();
        this.datumObjavljivanja = knjiga.getDatumObjavljivanja();
        this.brojStrana = knjiga.getBrojStrana();
        this.opis =knjiga.getOpis();
        this.zanr = knjiga.getZanr();
        this.ocena = knjiga.getOcena();
    }
}
