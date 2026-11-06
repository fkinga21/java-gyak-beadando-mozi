package com.example.mozi.repository;

import com.example.mozi.entity.Eloadas;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EloadasRepository extends JpaRepository<Eloadas, Integer> {

    @Query("SELECT e.mozi.varos, SUM(e.nezoszam) FROM Eloadas e " +
            "GROUP BY e.mozi.varos ORDER BY SUM(e.nezoszam) DESC")
    List<Object[]> varosonkentiNezok(Pageable pageable);

    @Query("SELECT e.film.cim, SUM(e.bevetel) FROM Eloadas e " +
            "GROUP BY e.film.id, e.film.cim ORDER BY SUM(e.bevetel) DESC")
    List<Object[]> filmenkentiBevetel(Pageable pageable);
}