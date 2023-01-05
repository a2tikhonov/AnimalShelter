package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuCatService;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/mainMenuCat")
public class MainMenuCatController extends MainMenuController {

    private final MainMenuCatService mainMenuCatService;

    public MainMenuCatController(MainMenuService mainMenuService, MainMenuCatService mainMenuCatService) {
        super(mainMenuService);
        this.mainMenuCatService = mainMenuCatService;
    }

    @Override
    @PutMapping
    public ResponseEntity<MainMenuButton> create(@RequestBody MainMenuButton mainMenuButton) {
        MainMenuButton mainMenuButton1 = mainMenuCatService.add(mainMenuButton);
        return ResponseEntity.ok(mainMenuButton1);
    }

    @Override
    @GetMapping
    public ResponseEntity<Collection<MainMenuButton>> getAll() {
        return ResponseEntity.ok(mainMenuCatService.getButtons());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<MainMenuButton> get(@PathVariable Long id) {
        MainMenuButton mainMenuButton = mainMenuCatService.get(id);
        return ResponseEntity.ok(mainMenuButton);
    }
}
