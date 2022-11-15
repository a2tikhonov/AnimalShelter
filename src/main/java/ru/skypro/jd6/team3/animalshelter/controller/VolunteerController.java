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

    /**
     * Поиск волонтера по идентификатору
     *
     * @param id - идентификатор волонтера
     */
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> findVolunteer(@PathVariable Long id) {
        Volunteer volunteer = volunteerService.findVolunteer(id);
        if (volunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    /**
     * Поиск всех волонтеров
     *
     */
    @GetMapping
    public ResponseEntity<Collection<Volunteer>> findVolunteers() {
        return ResponseEntity.ok(volunteerService.findAll());
    }

    /**
     * Добавление волонтера через браузер
     *
     * @param volunteer новый объект "Волонтер"
     */
    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer newVolunteer = volunteerService.addVolunteer(volunteer);
        return ResponseEntity.ok(newVolunteer);
    }

    /**
     * Замена волонтера через браузер
     *
     * @param volunteer новый объект "Волонтер"
     */
    @PutMapping
    public ResponseEntity<Volunteer> editVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer updateVolunteer = volunteerService.editVolunteer(volunteer);
        return ResponseEntity.ok(updateVolunteer);
    }

    /**
     * Удалить волонтера по идентификатору
     *
     * @param id - идентификатор волонтера
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }
}
