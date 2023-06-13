package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
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

    public RecenzijaDto(Recenzija recenzija) {
        this.id = recenzija.getId();
        this.ocena = recenzija.getOcena();
        this.tekst = recenzija.getTekst();
        this.datumRecenzije = recenzija.getDatumRecenzije();
        this.korisnik = recenzija.getKorisnik();
    }

    public RecenzijaDto() {}

    public RecenzijaDto(double ocena, String tekst, Date datumRecenzije) {
        this.ocena = ocena;
        this.tekst = tekst;
        this.datumRecenzije = datumRecenzije;
    }
}
