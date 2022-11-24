package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Cynologist;
import ru.skypro.jd6.team3.animalshelter.service.CynologistService;

/**
 * Контроллер для класса "Cynologist"
 */
@RestController
@RequestMapping("/cynologist")
public class CynologistController {

    private final CynologistService cynologistService;

    public CynologistController(CynologistService cynologistService) {
        this.cynologistService = cynologistService;
    }

    /**
     * Создаёт Кинолог в базе данных
     * @param cynologist новый объект "Кинолог"
     *
     */
    @PostMapping
    public ResponseEntity<Cynologist> createCynologist(@RequestBody Cynologist cynologist) {
        Cynologist newCynologist = cynologistService.createCynologist(cynologist);
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет кинолога в базе данных
     *
     * @return новый объект "Кинолог"
     */
    @PutMapping
    public ResponseEntity<Cynologist> updateCynologist(@RequestBody Cynologist cynologist) {
        Cynologist updateCynologist = cynologistService.updateCynologist(cynologist);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск кинолога по индификатору
     * @param id индификатор Кинолога
     *
     */
    @GetMapping("{id}")
    public ResponseEntity<Cynologist> getCynologist(@PathVariable Long id) {
        Cynologist cynologist = cynologistService.getCynologist(id);
        if (cynologist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Удаляет кинолога по индификатору
     * @param id индификатор кинолога
     *
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCynologist(@PathVariable Long id) {
        cynologistService.deleteCynologist(id);
        return ResponseEntity.ok().build();
    }
}
