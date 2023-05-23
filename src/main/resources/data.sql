INSERT INTO korisnik (ime, prezime, korisnicko_ime, mail, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Petar', 'Petrovic', 'nspera', 'pera.petrovic@gmail.com', 'dacabff', '2003-03-07', ' ', 'juhuuh', 'ADMINISTRATOR');
INSERT INTO korisnik (ime, prezime, korisnicko_ime, mail, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Predrag', 'Stojic', 'djape', 'nspredrag@gmail.com', 'tommy123', '1999-05-09', ' ', 'imam garazu', 'AUTOR');
INSERT INTO korisnik (ime, prezime, korisnicko_ime, mail, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Radojka', 'Markovic', 'rada', 'nsprada@gmail.com', 'djandja', '1995-04-07', ' ', 'volim grafove', 'AUTOR');
INSERT INTO korisnik (ime, prezime, korisnicko_ime, mail, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Buda', 'Bajic', 'budica', 'buda@gmail.com', 'analiza', '1990-07-07', ' ', 'volim analizu', 'CITALAC');

INSERT INTO zanr (naziv) VALUES ('strucna literatura');
INSERT INTO zanr (naziv) VALUES ('internet i racunari');
INSERT INTO zanr (naziv) VALUES ('edukativni');

INSERT INTO autor (je_aktivan, korisnikk_id) VALUES (1, 1);
INSERT INTO autor (je_aktivan, korisnikk_id) VALUES (1, 2);
INSERT INTO autor (je_aktivan, korisnikk_id) VALUES (1, 3);



INSERT INTO knjiga(naslov, naslovna_fotografija, isbn, datum_objavljivanja, broj_strana, opis, ocena, zanr) VALUES ( 'C++ PROGRAMSKI JEZIK', ' ', '978-93-96055-02-6', '2019-07-11', ' 999', 'Osnove objekno-orijentisanog programiranja kroz programski jezik C++', 5 , 'strucna literatura' );
INSERT INTO knjiga(naslov, naslovna_fotografija, isbn, datum_objavljivanja, broj_strana, opis, ocena, zanr) VALUES ('EXCEL ZA POCETNIKE', ' ', '978-92-95055-02-5', '2002-09-04', ' 103', 'Osnovni pojmovi', 3 , 'internet i racunari' );
INSERT INTO knjiga(naslov, naslovna_fotografija, isbn, datum_objavljivanja, broj_strana, opis, ocena, zanr) VALUES ('Teorija grafova', ' ', '978-94-94055-02-4', '2015-11-03', ' 155', 'osnovni pojmovi teorije grafova i reseni zadaci', 2.5 , 'internet i racunari' );


INSERT INTO autor_spisak_knjiga(autor_korisnikk_id, spisak_knjiga_naslov) VALUES(1, 'C++ PROGRAMSKI JEZIK');
INSERT INTO autor_spisak_knjiga(autor_korisnikk_id, spisak_knjiga_naslov) VALUES(1, 'EXCEL ZA POCETNIKE');

INSERT INTO recenzija (ocena, tekst, datum_recenzije, korisnik_id) VALUES (5, 'odlicna', '2019-03-03', 4);

INSERT INTO stavka_police(recenzija_id, knjiga_id) VALUES (1, 'EXCEL ZA POCETNIKE');

INSERT INTO polica (naziv, primarna) VALUES ('polica1', 1);
INSERT INTO polica (naziv, primarna) VALUES ('Want to Read', 1);
INSERT INTO polica (naziv, primarna) VALUES ('Currently Reading', 1);
INSERT INTO polica (naziv, primarna) VALUES ('Read', 1);


INSERT INTO korisnik_police(korisnik_korisnikk_id, police_id) VALUES (1, 1);

INSERT INTO polica_stavka_police(polica_id, stavka_police_id) VALUES (1, 1);
INSERT INTO zahtev_za_aktivaciju_naloga_autora (email, telefon, poruka, datum, status, autor_korisnikk_id) VALUES ('nsdespot@gmail.com', '0645541596', 'zahtev', '2022-01-02', 'ODOBREN', 1);