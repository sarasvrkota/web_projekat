package com.Projekat_Web.Projekat_Web.service;

import com.Projekat_Web.Projekat_Web.repository.PolicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicaService {

    @Autowired
    private PolicaRepository policaRepository;
}
