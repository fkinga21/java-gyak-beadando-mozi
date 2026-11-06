package com.example.mozi.repository;

import com.example.mozi.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmRepository extends JpaRepository<Film, Integer> {

    @Query("SELECT COALESCE(MAX(f.id), 0) FROM Film f")
    Integer maxId();
}