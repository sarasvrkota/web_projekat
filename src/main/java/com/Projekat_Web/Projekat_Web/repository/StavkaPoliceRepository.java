package com.Projekat_Web.Projekat_Web.repository;

import com.Projekat_Web.Projekat_Web.entity.StavkaPolice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StavkaPoliceRepository extends JpaRepository<StavkaPolice, Long> {
}