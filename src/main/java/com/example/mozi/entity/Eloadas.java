package com.example.mozi.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "eloadas")
public class Eloadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "filmid")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "moziid")
    private Mozi mozi;

    private LocalDate datum;
    private Integer nezoszam;
    private Integer bevetel;

    public Eloadas() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }

    public Mozi getMozi() { return mozi; }
    public void setMozi(Mozi mozi) { this.mozi = mozi; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public Integer getNezoszam() { return nezoszam; }
    public void setNezoszam(Integer nezoszam) { this.nezoszam = nezoszam; }

    public Integer getBevetel() { return bevetel; }
    public void setBevetel(Integer bevetel) { this.bevetel = bevetel; }
}