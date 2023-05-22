package com.Projekat_Web.Projekat_Web.dto;


import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @ToString
public class PolicaDto {

    private Long id;
    private String naziv;
    private boolean primarna;

    public PolicaDto(Long id, String naziv, boolean primarna) {
        this.id = id;
        this.naziv = naziv;
        this.primarna = primarna;
    }
}
