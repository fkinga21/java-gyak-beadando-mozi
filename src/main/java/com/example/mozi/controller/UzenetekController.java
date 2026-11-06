package com.example.mozi.controller;

import com.example.mozi.repository.UzenetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetekController {

    private final UzenetRepository uzenetRepository;

    public UzenetekController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {
        model.addAttribute("uzenetek", uzenetRepository.findAllByOrderByKuldesIdejeDesc());
        return "uzenetek";
    }
}