package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.Autor;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import com.Projekat_Web.Projekat_Web.entity.ZahtevZaAktivacijuNalogaAutora;
import com.Projekat_Web.Projekat_Web.service.ZahtevZaAktivacijuNalogaAutoraService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZahtevZaAktivacijuNalogaAutoraRepository extends JpaRepository<ZahtevZaAktivacijuNalogaAutora, Long> {

    ZahtevZaAktivacijuNalogaAutora findByEmail(String email);
    ZahtevZaAktivacijuNalogaAutora save(ZahtevZaAktivacijuNalogaAutora zahtevZaAktivacijuNalogaAutora);
    ZahtevZaAktivacijuNalogaAutora getById(Long id);
    List<ZahtevZaAktivacijuNalogaAutora> findAll();
    Optional<ZahtevZaAktivacijuNalogaAutora> findById(Long id);
    ZahtevZaAktivacijuNalogaAutora findByAutor(Autor autor);

}
