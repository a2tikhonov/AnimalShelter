package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/mainMenu")
public class MainMenuController {

    private final MainMenuService mainMenuService;

    public MainMenuController(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    @PutMapping
    public ResponseEntity<MainMenuButton> create(@RequestBody MainMenuButton mainMenuButton) {
        MainMenuButton mainMenuButton1 = mainMenuService.add(mainMenuButton);
        return ResponseEntity.ok(mainMenuButton1);
    }

    @GetMapping
    public ResponseEntity<Collection<MainMenuButton>> getAll() {
        return ResponseEntity.ok(mainMenuService.getButtons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainMenuButton> get(@PathVariable Long id) {
        MainMenuButton mainMenuButton = mainMenuService.get(id);
        return ResponseEntity.ok(mainMenuButton);
    }

}
