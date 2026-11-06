package com.example.mozi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzenetek")
public class Uzenet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A név megadása kötelező!")
    @Size(max = 100, message = "A név legfeljebb 100 karakter lehet!")
    private String nev;

    @NotBlank(message = "Az e-mail cím megadása kötelező!")
    @Email(message = "Érvénytelen e-mail cím!")
    @Size(max = 100, message = "Az e-mail cím legfeljebb 100 karakter lehet!")
    private String email;

    @NotBlank(message = "A tárgy megadása kötelező!")
    @Size(max = 150, message = "A tárgy legfeljebb 150 karakter lehet!")
    private String targy;

    @NotBlank(message = "Az üzenet szövegének megadása kötelező!")
    @Size(min = 10, max = 2000, message = "Az üzenet 10 és 2000 karakter közötti legyen!")
    @Column(length = 2000)
    private String szoveg;

    private LocalDateTime kuldesIdeje;

    @PrePersist
    void beallitIdo() {
        this.kuldesIdeje = LocalDateTime.now();
    }

    public Uzenet() {
    }

    public Long getId() { return id; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTargy() { return targy; }
    public void setTargy(String targy) { this.targy = targy; }

    public String getSzoveg() { return szoveg; }
    public void setSzoveg(String szoveg) { this.szoveg = szoveg; }

    public LocalDateTime getKuldesIdeje() { return kuldesIdeje; }
}