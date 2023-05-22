package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import lombok.*;

import java.util.Date;

@Getter @Setter @ToString

public class RecenzijaDto {

    private Long id;
    private double ocena;
    private String tekst;
    private Date datumRecenzije;
    private Korisnik korisnik;

    public RecenzijaDto(Long id, double ocena, String tekst, Date datumRecenzije, Korisnik korisnik) {
        this.id = id;
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
        this.korisnik = korisnik;
    }
}