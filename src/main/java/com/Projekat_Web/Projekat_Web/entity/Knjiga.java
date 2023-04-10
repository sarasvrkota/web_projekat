package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;

@Entity
public class Knjiga {
    @Column
    private String naslov;

    @Column
    private String photo;///???

    @Column
    private int broj_strana;

    @Column
    private double ocena;

//    @OneToOne( cascade = CascadeType.ALL)
//    @JoinColumn(name = "contact_id", referencedColumnName = "id")
//    private String zanr = new String();




}
