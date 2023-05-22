package com.Projekat_Web.Projekat_Web.dto;

import lombok.*;

@Getter @Setter @ToString
public class ZanrDto {

    private String naziv;

    public ZanrDto(String naziv) {
        this.naziv = naziv;
    }
}
