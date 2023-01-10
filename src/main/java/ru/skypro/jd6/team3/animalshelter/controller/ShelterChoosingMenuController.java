package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.ShelterChoosingMenu;
import ru.skypro.jd6.team3.animalshelter.service.ShelterChoosingMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/shelterChoosingMenu")
public class ShelterChoosingMenuController {

    private final ShelterChoosingMenuService shelterChoosingMenuService;

    public ShelterChoosingMenuController(ShelterChoosingMenuService shelterChoosingMenuService) {
        this.shelterChoosingMenuService = shelterChoosingMenuService;
    }

    @PutMapping
    public ResponseEntity<ShelterChoosingMenu> create(@RequestBody ShelterChoosingMenu shelterChoosingMenu) {
        ShelterChoosingMenu shelterChoosingMenu1 = shelterChoosingMenuService.add(shelterChoosingMenu);
        return ResponseEntity.ok(shelterChoosingMenu1);
    }

    @GetMapping
    public ResponseEntity<Collection<ShelterChoosingMenu>> getAllShelterChoices() {
        return ResponseEntity.ok(shelterChoosingMenuService.getButtons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShelterChoosingMenu> get(@PathVariable Long id) {
        ShelterChoosingMenu shelterChoosingMenu = shelterChoosingMenuService.get(id);
        return ResponseEntity.ok(shelterChoosingMenu);
    }
}
