package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.record.OwnerRecord;
import ru.skypro.jd6.team3.animalshelter.service.OwnerService;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    /**
     * Создаёт Хозяин в базе данных
     * @param ownerRecord Передаваемый упрощённый Хозяин
     * @return Возвращает полноценную сущность Хозяин из базы данных
     */
    @PostMapping
    public ResponseEntity<OwnerRecord> createOwner(@RequestBody OwnerRecord ownerRecord) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ownerService.createOwner(ownerRecord));
    }

    /**
     * Возвращает Хозяин из базы данных
     * @param id Параметр по которому мы ищём Хозяин в базе данных
     * @return Возвращает Хозин из базы данных если найден, если нет то сообщает об ошибке
     */
    @GetMapping("{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable Long id) {
        Owner owner = ownerService.getOwnerById(id);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Обновляет Хозяин в базе данных
     * @param ownerRecord Данные для обновления в базе данных
     * @return Возвращает обновлённую сущность из базы данных
     */
    @PutMapping
    public OwnerRecord updateOwner(@RequestBody OwnerRecord ownerRecord) {
        return ownerService.updateOwner(ownerRecord);
    }

    /**
     * Удаляет Хозяин из базы данных
     * @param id Параметр по которому ищется Хозяин в базе данных
     * @return Возвращает успшность данной операции
     */
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.ok().build();
    }
}
