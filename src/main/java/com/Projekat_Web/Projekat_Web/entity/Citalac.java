package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;

@Entity
@Getter @Setter @ToString
@Table(name = "citalac")
public class Citalac extends Korisnik implements Serializable {
}
