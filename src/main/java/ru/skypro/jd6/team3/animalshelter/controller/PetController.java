package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.service.PetService;

import java.util.Collection;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PutMapping
    public ResponseEntity<Pet> create(@RequestBody Pet pet) {
        Pet pet1 = petService.add(pet);
        return ResponseEntity.ok(pet1);
    }

    @GetMapping
    public ResponseEntity<Collection<Pet>> getAll() {
        return ResponseEntity.ok(petService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> get(@PathVariable Long id) {
        Pet pet = petService.get(id);
        return ResponseEntity.ok(pet);
    }
}
