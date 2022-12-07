package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwnerMenuButton;
import ru.skypro.jd6.team3.animalshelter.service.PotentialOwnerMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/consultMenu")
public class PotentialOwnerMenuController {

    private final PotentialOwnerMenuService potentialOwnerMenuService;

    public PotentialOwnerMenuController(PotentialOwnerMenuService potentialOwnerMenuService) {
        this.potentialOwnerMenuService = potentialOwnerMenuService;
    }

    @PutMapping
    public ResponseEntity<PotentialOwnerMenuButton> create(@RequestBody PotentialOwnerMenuButton potentialOwnerMenuButton) {
        PotentialOwnerMenuButton potentialOwnerMenuButton1 = potentialOwnerMenuService.add(potentialOwnerMenuButton);
        return ResponseEntity.ok(potentialOwnerMenuButton1);
    }

    @GetMapping
    public ResponseEntity<Collection<PotentialOwnerMenuButton>> getAll() {
        return ResponseEntity.ok(potentialOwnerMenuService.getButtons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PotentialOwnerMenuButton> get(@PathVariable Long id) {
        PotentialOwnerMenuButton potentialOwnerMenuButton = potentialOwnerMenuService.get(id);
        return ResponseEntity.ok(potentialOwnerMenuButton);
    }

}
