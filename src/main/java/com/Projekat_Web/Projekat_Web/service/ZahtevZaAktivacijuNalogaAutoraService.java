package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.repository.ZahtevZaAktivacijuNalogaAutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZahtevZaAktivacijuNalogaAutoraService {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;
}
