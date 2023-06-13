package com.Projekat_Web.Projekat_Web.dto;

import com.Projekat_Web.Projekat_Web.entity.Zanr;
import lombok.*;

@Getter @Setter @ToString
public class ZanrDto {

    private String naziv;

    public ZanrDto(String naziv) {
        this.naziv = naziv;
    }

    public ZanrDto(Zanr zanr) {
        this.naziv = zanr.getNaziv();
    }

    public ZanrDto() {}
}
