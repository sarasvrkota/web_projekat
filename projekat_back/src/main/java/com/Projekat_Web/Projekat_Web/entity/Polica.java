package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Getter @Setter @ToString
@Table(name = "polica")
public class Polica implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String naziv;

    @Column
    private boolean primarna;

    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "stavka")
    private Set<StavkaPolice> stavkaPolice = new HashSet<>();

    public Polica(String naziv, boolean primarna, Set<StavkaPolice> stavkaPolice){
        this.naziv = naziv;
        this.primarna = primarna;
        this.stavkaPolice = stavkaPolice;
    }
    public Polica(String naziv, boolean primarna){
        this.naziv = naziv;
        this.primarna = primarna;
    }
    public Polica(){}


    //public Set<StavkaPolice> getStavkaPolice() { return this.stavkaPolice;}
}
