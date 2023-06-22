package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @ToString
@Table(name = "autor")
public class Autor extends Korisnik implements Serializable {
    // u konstruktoru obaevzno staviti da je uloga = AUTOR

    @Column(name = "je_aktivan")
    private boolean jeAktivan;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "spisak_knjiga")
    private Set<Knjiga> spisakKnjiga = new HashSet<>();

    public Autor(Korisnik k, boolean jeAktivan, HashSet<Knjiga> spisakKnjiga) {
        this.ime = k.ime;
        this. prezime = k.prezime;
        this.korisnickoIme = k.korisnickoIme;
        this.mail = k.mail;
        this.lozinka = k.lozinka;
        this.datumRodjenja = k.datumRodjenja;
        this.profilnaSlika = k.profilnaSlika;
        this.opis = k.opis;
        this.uloga = k.uloga;
        this.jeAktivan = jeAktivan;
        this.spisakKnjiga = spisakKnjiga;
    }

    public Autor(String ime, String prezime, String mail, LocalDate datumRodjenja,
                 String profilnaSlika, String opis, Uloga uloga, boolean jeAktivan) {

        //super(ime, prezime, mail, datumRodjenja, profilnaSlika, opis, uloga);
        this.ime = ime;
        this.prezime = prezime;
        this.mail= mail;
        this.datumRodjenja = datumRodjenja;
        this.profilnaSlika = profilnaSlika;
        this.opis = opis;
        this.uloga = uloga;
        this.jeAktivan = jeAktivan;
    }

    public Autor(String ime, String prezime, String korisnickoIme, String mail, String lozinka, LocalDate datumRodjenja,
                 String profilnaSlika, String opis, Uloga uloga, boolean jeAktivan, Set<Knjiga> spisakKnjiga) {
        super(ime, prezime, korisnickoIme, mail, lozinka, datumRodjenja, profilnaSlika, opis, uloga);
        this.jeAktivan = jeAktivan;
        this.spisakKnjiga = spisakKnjiga;
    }

    /*   @ManyToMany
    @JoinTable(name = "spisak_knjiga",
            joinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "autor_korisnikk_id"),
            inverseJoinColumns = @JoinColumn(name = "knjiga_id", referencedColumnName = "id"))
    private Set<Knjiga> spisak_knjiga = new HashSet<>();*/

    public Autor() {}

    public Autor(String ime, String prezime, String korisnickoIme, String mail, String lozinka) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.mail = mail;
        this.lozinka = lozinka;
        this.jeAktivan = false;

    }








}
