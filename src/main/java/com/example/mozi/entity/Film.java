package com.example.mozi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "film")
public class Film {

    @Id
    private Integer id;

    @NotBlank(message = "A cím megadása kötelező!")
    @Size(max = 100, message = "A cím legfeljebb 100 karakter lehet!")
    private String cim;

    @NotNull(message = "Az év megadása kötelező!")
    @Min(value = 1890, message = "Az évszám 1890 és 2100 között legyen!")
    @Max(value = 2100, message = "Az évszám 1890 és 2100 között legyen!")
    private Integer ev;

    @NotNull(message = "A hossz megadása kötelező!")
    @Min(value = 1, message = "A hossz 1 és 600 perc között legyen!")
    @Max(value = 600, message = "A hossz 1 és 600 perc között legyen!")
    private Integer hossz;

    public Film() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCim() { return cim; }
    public void setCim(String cim) { this.cim = cim; }

    public Integer getEv() { return ev; }
    public void setEv(Integer ev) { this.ev = ev; }

    public Integer getHossz() { return hossz; }
    public void setHossz(Integer hossz) { this.hossz = hossz; }
}