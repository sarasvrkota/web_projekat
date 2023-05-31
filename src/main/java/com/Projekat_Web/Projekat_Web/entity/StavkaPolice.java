package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;

@Entity
@Getter @Setter @ToString
@Table(name = "stavka_police")
public class StavkaPolice implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "knjiga_id", referencedColumnName = "naslov")
    private Knjiga knjiga;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recenzija_id", referencedColumnName = "id")
    private Recenzija recenzija;

    public StavkaPolice(Knjiga knjiga, Recenzija recenzija) {
        this.knjiga = knjiga;
        this.recenzija = recenzija;
    }


/*    @ManyToOne(fetch = FetchType.LAZY)
    private Polica polica;*/

// da li jedna stavka police moze biti na vise polica????????
}
