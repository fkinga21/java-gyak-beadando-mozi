package com.example.mozi.repository;

import com.example.mozi.entity.Uzenet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UzenetRepository extends JpaRepository<Uzenet, Long> {
    List<Uzenet> findAllByOrderByKuldesIdejeDesc();
}