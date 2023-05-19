package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.service.KnjigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnjigaRestController {

    @Autowired
    private KnjigaService knjigaService;


}
