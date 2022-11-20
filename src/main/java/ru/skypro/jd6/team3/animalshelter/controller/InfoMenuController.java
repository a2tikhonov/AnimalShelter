package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.InfoMenu;
import ru.skypro.jd6.team3.animalshelter.service.InfoMenuService;

@RestController
@RequestMapping("/infoMenu")
public class InfoMenuController {

    private final InfoMenuService infoMenuService;

    public InfoMenuController(InfoMenuService infoMenuService) {
        this.infoMenuService = infoMenuService;
    }

    @PutMapping
    public ResponseEntity<InfoMenu> create(@RequestBody InfoMenu infoMenu) {
        InfoMenu infoMenu1 = infoMenuService.add(infoMenu);
        return ResponseEntity.ok(infoMenu1);
    }

    @GetMapping
    public ResponseEntity<InfoMenu> get(@RequestParam Long id) {
        InfoMenu infoMenu = infoMenuService.get(id);
        return ResponseEntity.ok(infoMenu);
    }
}
