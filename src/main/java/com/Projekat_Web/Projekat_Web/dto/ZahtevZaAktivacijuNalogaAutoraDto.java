package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
public class ZahtevZaAktivacijuNalogaAutoraDto {


    public enum StatusDto {
        NA_CEKANJU, ODOBREN, ODBIJEN;
    }

    private String email;
    private String telefon;
    private String poruka;
    private Long id;
    //private Korisnik korisnik;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ZahtevZaAktivacijuNalogaAutoraDto () {}
    public ZahtevZaAktivacijuNalogaAutoraDto(String email, String telefon, String poruka) {

        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;

    }

    public ZahtevZaAktivacijuNalogaAutoraDto(String email, String telefon, String poruka, Long id_autora, StatusDto status) {
        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;
        this.id = id_autora;

    }

    public ZahtevZaAktivacijuNalogaAutoraDto(String email, String telefon, String poruka, Long id_autora,
                                             ZahtevZaAktivacijuNalogaAutora.Status status) {
        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;
        this.id = id_autora;
        this.status = status;

    }

    public ZahtevZaAktivacijuNalogaAutoraDto(ZahtevZaAktivacijuNalogaAutora zahtevZaAktivacijuNalogaAutora) {
        this.email = zahtevZaAktivacijuNalogaAutora.getEmail();
        this.telefon = zahtevZaAktivacijuNalogaAutora.getTelefon();
        this.poruka = zahtevZaAktivacijuNalogaAutora.getPoruka();
        this.id = zahtevZaAktivacijuNalogaAutora.getAutor().getId();
        this.status = zahtevZaAktivacijuNalogaAutora.getStatus();
        //this.korisnik = zahtevZaAktivacijuNalogaAutora.getAutor();

    }
}
