package com.example.mozi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "mozi")
public class Mozi {

    @Id
    private Integer id;

    @NotBlank(message = "A név megadása kötelező!")
    @Size(max = 100, message = "A név legfeljebb 100 karakter lehet!")
    private String nev;

    @NotBlank(message = "A város megadása kötelező!")
    @Size(max = 60, message = "A város legfeljebb 60 karakter lehet!")
    private String varos;

    @NotNull(message = "A férőhely megadása kötelező!")
    @Min(value = 1, message = "A férőhely legalább 1 legyen!")
    private Integer ferohely;

    public Mozi() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getVaros() { return varos; }
    public void setVaros(String varos) { this.varos = varos; }

    public Integer getFerohely() { return ferohely; }
    public void setFerohely(Integer ferohely) { this.ferohely = ferohely; }
}