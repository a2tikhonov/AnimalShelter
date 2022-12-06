package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.service.VolunteerService;

import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public ResponseEntity<Volunteer> add(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.save(volunteer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> get(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.get(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Volunteer>> get() {
        return ResponseEntity.ok(volunteerService.get());
    }
}
