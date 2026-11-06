package com.example.mozi.controller;

import com.example.mozi.repository.EloadasRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DiagramController {

    private final EloadasRepository eloadasRepository;

    public DiagramController(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping("/diagram")
    public String diagram(Model model) {
        List<Object[]> varosok = eloadasRepository.varosonkentiNezok(PageRequest.of(0, 10));
        List<Object[]> filmek = eloadasRepository.filmenkentiBevetel(PageRequest.of(0, 10));

        model.addAttribute("varosCimkek", varosok.stream().map(o -> (String) o[0]).toList());
        model.addAttribute("varosAdatok", varosok.stream().map(o -> ((Number) o[1]).longValue()).toList());
        model.addAttribute("filmCimkek", filmek.stream().map(o -> (String) o[0]).toList());
        model.addAttribute("filmAdatok", filmek.stream().map(o -> ((Number) o[1]).longValue()).toList());
        return "diagram";
    }
}