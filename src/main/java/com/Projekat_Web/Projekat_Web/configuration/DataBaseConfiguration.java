package com.Projekat_Web.Projekat_Web.configuration;


import com.Projekat_Web.Projekat_Web.entity.Korisnik;
import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import com.Projekat_Web.Projekat_Web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.Projekat_Web.Projekat_Web.entity.Korisnik.Uloga.*;


@Configuration
public class DataBaseConfiguration {

    @Autowired
    private KorisnikRepository korisnikRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private KnjigaRepository knjigaRepository;

    @Autowired
    private PolicaRepository policaRepository;

    @Autowired
    private RecenzijaRepository recenzijaRepository;

    @Autowired
    private StavkaPoliceRepository stavkaPoliceRepository;

    @Autowired
    private ZahtevZaAktivacijuNalogaAutoraRepository zahtevZaAktivacijuNalogaAutoraRepository;

    @Autowired
    private ZanrRepository zanrRepository;

    @Bean
    public boolean instantiate(){
        Korisnik korisnik1 = new Korisnik( "Sara", "Svrkota", "sarasaki", "volimdjacko@gmail.com",
                                            "volimkarbonaru", LocalDate.of(1999, 5, 9), " ",
                                            "nasledjivanje nije kada", AUTOR);
        korisnikRepository.save(korisnik1);


        /*Department department1 = new Department("first department");
        Department department2 = new Department("second department");

        department1.setCompany(company);
        department2.setCompany(company);
        departmentRepository.saveAll(
                List.of(department1, department2)
        );

        Employee pera = new Employee(
                "peraperic","pera123","Pera", "Peric", "Rukovodilac", department1
        );
        Employee mika = new Employee(
                "mikamikic", "mika123","Mika", "Mikic", "Menadzer", department1
        );
        Employee zika = new Employee(
                "zikazikic","zika123","Zika", "Zikic", "Radnik", department2
        );

        employeeRepository.saveAll(
                List.of(pera, mika, zika)
        );

        Project project1 = new Project(
                "Projekat 1", new Date(125, Calendar.JULY, 4)
        );

        Project project2 = new Project(
                "Projekat 2", new Date(129, Calendar.DECEMBER, 3)
        );

        projectRepository.saveAll(
                List.of(project1, project2)
        );

        mika.getProjects().add(project1);
        mika.getProjects().add(project2);

        zika.getProjects().add(project2);

        employeeRepository.save(mika);
        employeeRepository.save(zika);*/

        return true;
    }




}
