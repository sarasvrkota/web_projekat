package com.Projekat_Web.Projekat_Web.dto;

import java.time.LocalDate;

public class AutorDto {

    private Long id;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String mail;
    private String lozinka;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private String uloga;
    private boolean jeAktivan;

    public AutorDto(Long id, String ime, String prezime, String korisnickoIme, String mail, String lozinka,
                    LocalDate datumRodjenja, String profilnaSlika, String opis, String uloga, boolean jeAktivan) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.lozinka = lozinka;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
        this.jeAktivan = jeAktivan;
    }
}
