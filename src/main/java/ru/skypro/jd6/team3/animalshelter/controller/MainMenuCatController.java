package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonCat;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonDog;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuCatService;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/mainMenuCat")
public class MainMenuCatController {

    private final MainMenuCatService mainMenuCatService;

    public MainMenuCatController(MainMenuCatService mainMenuCatService) {
        this.mainMenuCatService = mainMenuCatService;
    }

    @PutMapping
    public ResponseEntity<MainMenuButtonCat> create(@RequestBody MainMenuButtonCat mainMenuButtonCat) {
        MainMenuButtonCat mainMenuButtonCat1 = mainMenuCatService.add(mainMenuButtonCat);
        return ResponseEntity.ok(mainMenuButtonCat1);
    }

    @GetMapping
    public ResponseEntity<Collection<MainMenuButtonCat>> getAllCats() {
        return ResponseEntity.ok(mainMenuCatService.getButtons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainMenuButtonCat> get(@PathVariable Long id) {
        MainMenuButtonCat mainMenuButtonCat = mainMenuCatService.get(id);
        return ResponseEntity.ok(mainMenuButtonCat);
    }
}
