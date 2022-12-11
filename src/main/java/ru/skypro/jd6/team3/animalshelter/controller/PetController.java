package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;

import ru.skypro.jd6.team3.animalshelter.service.PetService;

/**
 * Контроллер для класса "Pet"
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    /**
     * Создаёт Питомец в базе данных
     * @param pet Передаваемый упрощённый Питомец
     * @return Возвращает полноценную сущность Питомец из базы данных
     */
    @PostMapping
    public ResponseEntity<Pet> createPet(@RequestBody Pet pet) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(petService.createPet(pet));
    }

    /**
     * Возвращает Питомец из базы данных
     * @param id Параметр по которому мы ищём Питомец в базе данных
     * @return Возвращает Питомец из базы данных если найден, если нет то сообщает об ошибке
     */
    @GetMapping("{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        Pet pet = petService.getPet(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет Питомец в базе данных
     *
     * @return Возвращает обновлённую сущность из базы данных
     */
    @PutMapping
    public Pet updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    /**
     * Удаляет Питомец из базы данных
     * @param id Параметр по которому ищется Хозяин в базе данных
     * @return Возвращает успшность данной операции
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }
}
