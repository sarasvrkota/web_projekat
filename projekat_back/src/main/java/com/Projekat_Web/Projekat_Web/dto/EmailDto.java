package com.Projekat_Web.Projekat_Web.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDto {

    private String to;
    private String subject;
    private String message;

    public EmailDto() {
    }

    public EmailDto(String to, String subject, String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
