package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.service.ZahtevZaAktivacijuNalogaAutoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZahtevZaAktivacijuNalogaAutoraRestController {

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraService zahtevZaAktivacijuNalogaAutoraService;
}
