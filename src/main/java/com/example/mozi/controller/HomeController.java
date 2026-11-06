package com.example.mozi.controller;

import com.example.mozi.repository.EloadasRepository;
import com.example.mozi.repository.FilmRepository;
import com.example.mozi.repository.MoziRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final FilmRepository filmRepository;
    private final MoziRepository moziRepository;
    private final EloadasRepository eloadasRepository;

    public HomeController(FilmRepository filmRepository,
                          MoziRepository moziRepository,
                          EloadasRepository eloadasRepository) {
        this.filmRepository = filmRepository;
        this.moziRepository = moziRepository;
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("filmSzam", filmRepository.count());
        model.addAttribute("moziSzam", moziRepository.count());
        model.addAttribute("eloadasSzam", eloadasRepository.count());
        return "home";
    }
}