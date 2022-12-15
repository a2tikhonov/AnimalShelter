package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;
import ru.skypro.jd6.team3.animalshelter.service.NewUserMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/newUserMenu")
public class NewUserMenuController {

    private final NewUserMenuService newUserMenuService;

    public NewUserMenuController(NewUserMenuService newUserMenuService) {
        this.newUserMenuService = newUserMenuService;
    }

    @PostMapping
    public ResponseEntity<NewUserMenuButton> create(@RequestBody NewUserMenuButton newUserMenuButton) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUserMenuService.add(newUserMenuButton));
    }

    @PutMapping
    public ResponseEntity<NewUserMenuButton> add(@RequestBody NewUserMenuButton newUserMenuButton) {
        NewUserMenuButton newUserMenuButton1 = newUserMenuService.add(newUserMenuButton);
        return ResponseEntity.ok(newUserMenuButton1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewUserMenuButton> get(@PathVariable Long id) {
        NewUserMenuButton newUserMenuButton = newUserMenuService.get(id);
        return ResponseEntity.ok(newUserMenuButton);
    }

    @GetMapping()
    public ResponseEntity<Collection<NewUserMenuButton>> getAll() {
        Collection<NewUserMenuButton> buttons = newUserMenuService.getButtons();
        return ResponseEntity.ok(buttons);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        newUserMenuService.delete(id);
        return ResponseEntity.ok().build();
    }
}
