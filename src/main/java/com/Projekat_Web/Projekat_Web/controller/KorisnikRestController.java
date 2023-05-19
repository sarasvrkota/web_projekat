package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.service.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KorisnikRestController {
    @Autowired
    private KorisnikService korisnikService;
}
