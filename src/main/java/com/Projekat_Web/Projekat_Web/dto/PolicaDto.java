package com.Projekat_Web.Projekat_Web.dto;


import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter @Setter @ToString
public class PolicaDto {

    private Long id;
    private String naziv;
    private boolean primarna;
    private Set<StavkaPolice> stavkaPolice = new HashSet<>();

    public PolicaDto(Long id, String naziv, boolean primarna, Set<StavkaPolice> stavkaPolice) {
        this.id = id;
        this.naziv = naziv;
        this.primarna = primarna;
        this.stavkaPolice = stavkaPolice;
    }
}
