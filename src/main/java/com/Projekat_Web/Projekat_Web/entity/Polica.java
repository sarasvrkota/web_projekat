package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "polica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "stavka_police")
    private Set<StavkaPolice> stavkaPolice = new HashSet<>();





}
