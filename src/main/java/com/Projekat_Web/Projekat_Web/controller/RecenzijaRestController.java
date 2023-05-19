package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.service.RecenzijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecenzijaRestController {

    @Autowired
    private RecenzijaService recenzijaService;
}
