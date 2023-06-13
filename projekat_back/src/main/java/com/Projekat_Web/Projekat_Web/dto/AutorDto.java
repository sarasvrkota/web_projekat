package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.Projekat_Web.Projekat_Web.entity.Korisnik.Uloga.AUTOR;

@Getter @Setter @ToString
public class AutorDto {


    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String mail;
    private String lozinka;
    private LocalDate datumRodjenja;
    private String profilnaSlika;
    private String opis;
    private Autor.Uloga uloga;
    private boolean jeAktivan;
    private Set<Knjiga> spisakKnjiga = new HashSet<>();

    public AutorDto(String ime, String prezime, String korisnickoIme, String mail, String lozinka,
                    LocalDate datumRodjenja, String profilnaSlika, String opis, Autor.Uloga uloga, boolean jeAktivan, Set<Knjiga> spisakKnjiga) {

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
        this.spisakKnjiga = spisakKnjiga;
    }

    public AutorDto(Autor autor) {
        this.ime = autor.getIme();
        this.prezime = autor.getPrezime();
        this.korisnickoIme = autor.getKorisnickoIme();
        this.mail = autor.getMail();
        this.lozinka = autor.getLozinka();
        this.datumRodjenja = autor.getDatumRodjenja();
        this.profilnaSlika = autor.getProfilnaSlika();
        this.opis = autor.getOpis();
        this.uloga = autor.getUloga();
        this.jeAktivan = autor.isJeAktivan();
        this.spisakKnjiga = autor.getSpisakKnjiga();
    }
}
