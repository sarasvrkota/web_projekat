package com.Projekat_Web.Projekat_Web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.*;

@Entity
@Getter @Setter @ToString
@Table(name = "administrator")
public class Administrator extends Korisnik implements Serializable {

}
