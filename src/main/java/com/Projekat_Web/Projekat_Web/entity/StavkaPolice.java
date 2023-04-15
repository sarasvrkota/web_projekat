package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;

@Entity
@Getter @Setter @ToString
@Table(name = "stavka_police")
public class StavkaPolice implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "knjiga_id", referencedColumnName = "id")
    private Knjiga knjiga;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recenzija_id", referencedColumnName = "id")
    private Recenzija recenzija;

// da li jedna stavka police moze biti na vise polica????????
}
