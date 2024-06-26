package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
//import java.time.LocalDate;
import java.util.Date;


@Entity
@Getter @Setter @ToString
@Table(name = "zahtev_za_aktivaciju_naloga_autora")
public class ZahtevZaAktivacijuNalogaAutora implements Serializable {



    public enum Status {NA_CEKANJU, ODBIJEN, ODOBREN}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zahtev_id") //PRIMARNI KLJUC , u bazi se tako zove kolona
    private Long id;
    @Column
    private String email;

    @Column
    private String telefon;

    @Column
    private String poruka;

    @Column
    private Date datum;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    private Autor autor;


}
