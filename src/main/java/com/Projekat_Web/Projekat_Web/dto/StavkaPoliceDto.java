package com.Projekat_Web.Projekat_Web.dto;
import com.Projekat_Web.Projekat_Web.entity.Knjiga;
import com.Projekat_Web.Projekat_Web.entity.Recenzija;
import lombok.*;

@Getter @Setter @ToString
public class StavkaPoliceDto {

    private Long id;
    private Knjiga knjiga;
    private Recenzija recenzija;

    public StavkaPoliceDto(Long id, Knjiga knjiga, Recenzija recenzija) {
        this.id = id;
        this.knjiga = knjiga;
        this.recenzija = recenzija;
    }
}
