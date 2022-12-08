package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.service.PotentialOwnerService;

import java.util.Collection;

@RestController
@RequestMapping("/potential_owner")
public class PotentialOwnerController {

    PotentialOwnerService potentialOwnerService;

    public PotentialOwnerController(PotentialOwnerService potentialOwnerService) {
        this.potentialOwnerService = potentialOwnerService;
    }

    @PutMapping
    public ResponseEntity<PotentialOwner> create(@RequestBody PotentialOwner potentialOwner) {
        PotentialOwner potentialOwner1 = potentialOwnerService.save(potentialOwner);
        return ResponseEntity.ok(potentialOwner1);
    }

    @GetMapping
    public ResponseEntity<Collection<PotentialOwner>> getAll() {
        return ResponseEntity.ok(potentialOwnerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PotentialOwner> get(@PathVariable Long id) {
        PotentialOwner potentialOwner = potentialOwnerService.get(id);
        return ResponseEntity.ok(potentialOwner);
    }
}
