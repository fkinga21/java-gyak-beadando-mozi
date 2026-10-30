package com.example.mozi.controller;

import com.example.mozi.entity.Eloadas;
import com.example.mozi.repository.EloadasRepository;
import com.example.mozi.repository.FilmRepository;
import com.example.mozi.repository.MoziRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdatbazisController {

    private final FilmRepository filmRepository;
    private final MoziRepository moziRepository;
    private final EloadasRepository eloadasRepository;

    public AdatbazisController(FilmRepository filmRepository,
                               MoziRepository moziRepository,
                               EloadasRepository eloadasRepository) {
        this.filmRepository = filmRepository;
        this.moziRepository = moziRepository;
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping("/adatbazis")
    public String adatbazis(@RequestParam(defaultValue = "0") int page, Model model) {
        if (page < 0) {
            page = 0;
        }
        Page<Eloadas> eloadasok = eloadasRepository.findAll(
                PageRequest.of(page, 20, Sort.by("datum", "id")));

        model.addAttribute("filmek", filmRepository.findAll(Sort.by("id")));
        model.addAttribute("mozik", moziRepository.findAll(Sort.by("id")));
        model.addAttribute("eloadasok", eloadasok);
        return "adatbazis";
    }
}