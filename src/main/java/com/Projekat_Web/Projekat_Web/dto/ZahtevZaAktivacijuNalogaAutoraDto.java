package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter @Setter @ToString
public class ZahtevZaAktivacijuNalogaAutoraDto {

    private Long id;
    private String email;
    private String telefon;
    private String poruka;
    private Date datum;
    private StatusZahteva status;
    private Autor autor;

    public ZahtevZaAktivacijuNalogaAutoraDto(Long id, String email, String telefon,
                                             String poruka, Date datum, StatusZahteva status,
                                             Autor autor) {
        this.id = id;
        this.email = email;
        this.telefon = telefon;
        this.poruka = poruka;
        this.datum = datum;
        this.status = status;
        this.autor = autor;
    }
}
