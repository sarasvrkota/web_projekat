package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.Column;

public class Recenzija {

    @Column
    private double ocena;

    @Column
    private String tekst;

    @Column
    private String datum; //string ili date??

    //korisnik kompozicija???






}
