package com.example.mozi.controller;

import com.example.mozi.entity.Mozi;
import com.example.mozi.repository.MoziRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mozik")
public class MoziRestController {

    private final MoziRepository moziRepository;

    public MoziRestController(MoziRepository moziRepository) {
        this.moziRepository = moziRepository;
    }

    // GET /api/mozik: az összes mozi
    @GetMapping
    public List<Mozi> osszes() {
        return moziRepository.findAll(Sort.by("id"));
    }

    // GET /api/mozik/{id}: egy mozi
    @GetMapping("/{id}")
    public ResponseEntity<?> egy(@PathVariable Integer id) {
        return moziRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> nemTalalhato(id));
    }

    // POST /api/mozik: új mozi (csak admin)
    @PostMapping
    public ResponseEntity<Mozi> letrehoz(@Valid @RequestBody Mozi mozi) {
        mozi.setId(moziRepository.maxId() + 1);
        Mozi mentett = moziRepository.save(mozi);
        URI hely = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(mentett.getId()).toUri();
        return ResponseEntity.created(hely).body(mentett);
    }

    // PUT /api/mozik/{id}: módosítás (csak admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> modosit(@PathVariable Integer id, @Valid @RequestBody Mozi adatok) {
        return moziRepository.findById(id)
                .<ResponseEntity<?>>map(mozi -> {
                    mozi.setNev(adatok.getNev());
                    mozi.setVaros(adatok.getVaros());
                    mozi.setFerohely(adatok.getFerohely());
                    return ResponseEntity.ok(moziRepository.save(mozi));
                })
                .orElseGet(() -> nemTalalhato(id));
    }

    // DELETE /api/mozik/{id}: törlés (csak admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> torol(@PathVariable Integer id) {
        if (!moziRepository.existsById(id)) {
            return nemTalalhato(id);
        }
        try {
            moziRepository.deleteById(id);
            moziRepository.flush();
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("hiba", "A mozi nem törölhető, mert tartoznak hozzá előadások."));
        }
    }

    private ResponseEntity<?> nemTalalhato(Integer id) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("hiba", "A mozi nem található: " + id));
    }
}