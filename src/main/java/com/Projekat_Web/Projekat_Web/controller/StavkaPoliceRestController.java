package com.Projekat_Web.Projekat_Web.controller;

import com.Projekat_Web.Projekat_Web.service.StavkaPoliceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StavkaPoliceRestController {

    @Autowired
    private StavkaPoliceService stavkaPoliceService;
}
