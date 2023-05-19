package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.repository.KnjigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnjigaService {

    @Autowired
    private KnjigaRepository knjigaRepository;
}
