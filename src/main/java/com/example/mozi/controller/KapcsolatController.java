package com.example.mozi.controller;

import com.example.mozi.entity.Uzenet;
import com.example.mozi.repository.UzenetRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KapcsolatController {

    private final UzenetRepository uzenetRepository;

    public KapcsolatController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    @GetMapping("/kapcsolat")
    public String urlap(Model model) {
        model.addAttribute("uzenet", new Uzenet());
        return "kapcsolat";
    }

    @PostMapping("/kapcsolat")
    public String kuldes(@Valid @ModelAttribute("uzenet") Uzenet uzenet,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "kapcsolat";
        }
        uzenetRepository.save(uzenet);
        redirectAttributes.addFlashAttribute("siker", "Köszönjük! Üzenetét elmentettük.");
        return "redirect:/kapcsolat";
    }
}