package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
public class ZahtevZaAktivacijuNalogaAutoraDto {


    private String email;
    private String telefon;
    private String poruka;


    public ZahtevZaAktivacijuNalogaAutoraDto(String email, String telefon, String poruka) {

        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;

    }
}
