package com.example.mozi.repository;

import com.example.mozi.entity.Mozi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MoziRepository extends JpaRepository<Mozi, Integer> {

    @Query("SELECT COALESCE(MAX(m.id), 0) FROM Mozi m")
    Integer maxId();
}