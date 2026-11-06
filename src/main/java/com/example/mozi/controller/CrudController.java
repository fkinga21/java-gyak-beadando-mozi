package com.example.mozi.controller;

import com.example.mozi.entity.Film;
import com.example.mozi.repository.FilmRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/crud")
public class CrudController {

    private final FilmRepository filmRepository;

    public CrudController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // READ: lista
    @GetMapping
    public String lista(Model model) {
        model.addAttribute("filmek", filmRepository.findAll(Sort.by("id")));
        return "crud";
    }

    // CREATE: üres űrlap
    @GetMapping("/uj")
    public String ujUrlap(Model model) {
        model.addAttribute("film", new Film());
        return "crud-urlap";
    }

    // UPDATE: kitöltött űrlap
    @GetMapping("/szerkeszt/{id}")
    public String szerkesztUrlap(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) {
            ra.addFlashAttribute("hiba", "A film nem található.");
            return "redirect:/crud";
        }
        model.addAttribute("film", film.get());
        return "crud-urlap";
    }

    // CREATE és UPDATE mentése
    @PostMapping("/mentes")
    public String mentes(@Valid @ModelAttribute("film") Film film,
                         BindingResult result,
                         RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "crud-urlap";
        }
        boolean uj = film.getId() == null;
        if (uj) {
            film.setId(filmRepository.maxId() + 1);
        }
        filmRepository.save(film);
        ra.addFlashAttribute("siker", uj ? "A film hozzáadva." : "A film módosítva.");
        return "redirect:/crud";
    }

    // DELETE
    @PostMapping("/torles/{id}")
    public String torles(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            filmRepository.deleteById(id);
            filmRepository.flush();
            ra.addFlashAttribute("siker", "A film törölve.");
        } catch (DataIntegrityViolationException e) {
            ra.addFlashAttribute("hiba",
                    "A film nem törölhető, mert tartoznak hozzá előadások.");
        }
        return "redirect:/crud";
    }
}