package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuDogService;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/mainMenuDog")
public class MainMenuDogController extends MainMenuController {

    private final MainMenuDogService mainMenuDogService;

    public MainMenuDogController(MainMenuService mainMenuService, MainMenuDogService mainMenuDogService) {
        super(mainMenuService);
        this.mainMenuDogService = mainMenuDogService;
    }

    @Override
    @PutMapping
    public ResponseEntity<MainMenuButton> create(@RequestBody MainMenuButton mainMenuButton) {
        MainMenuButton mainMenuButton1 = mainMenuDogService.add(mainMenuButton);
        return ResponseEntity.ok(mainMenuButton1);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<MainMenuButton>> getAll() {
        return ResponseEntity.ok(mainMenuDogService.getButtons());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<MainMenuButton> get(@PathVariable Long id) {
        MainMenuButton mainMenuButton = mainMenuDogService.get(id);
        return ResponseEntity.ok(mainMenuButton);
    }
}
