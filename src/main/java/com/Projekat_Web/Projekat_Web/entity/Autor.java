package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "autor")
public class Autor extends Korisnik implements Serializable {
    // u konstruktoru obaevzno staviti da je uloga = AUTOR
    @Column(name = "je_aktivan")
    private boolean jeAktivan;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "spisak_knjiga")
    private Set<Knjiga> spisakKnjiga = new HashSet<>();






}
