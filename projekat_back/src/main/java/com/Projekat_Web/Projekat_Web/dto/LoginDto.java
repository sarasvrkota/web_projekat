package com.Projekat_Web.Projekat_Web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginDto {

    private String mail;

    private String lozinka;

    public LoginDto() {
    }

    public LoginDto(String mail, String lozinka) {
        this.mail = mail;
        this.lozinka = lozinka;
    }


}
