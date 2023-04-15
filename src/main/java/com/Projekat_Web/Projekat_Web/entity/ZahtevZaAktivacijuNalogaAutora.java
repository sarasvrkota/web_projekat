package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
//import java.time.LocalDate;
import java.util.Date;

enum StatusZahteva {NA_CEKANJU, ODBIJEN, ODOBREN}
@Entity
@Getter @Setter @ToString
@Table(name = "zahtev_za_aktivaciju_naloga_autora")
public class ZahtevZaAktivacijuNalogaAutora implements Serializable {

    @Column
    private String email;

    @Column
    private String telefon;

    @Column
    private String poruka;

    @Column
    private Date datum;

    @Column
    private StatusZahteva status;

}
