package ru.skypro.jd6.team3.animalshelter.GlobalExceptionHandler;
/**
 * Обработка всех ошибок
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.jd6.team3.animalshelter.exception.CynologistNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.PetNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.ShelterNotFoundException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<String> handelOwnerNotFoundException(OwnerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Хозяин не найден");
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<String> handelPetNotFoundException(PetNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Питомец не найден");
    }

    @ExceptionHandler(CynologistNotFoundException.class)
    public ResponseEntity<String> handelCynologistNotFoundException(CynologistNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Кинолог не найден");
    }

    @ExceptionHandler(ShelterNotFoundException.class)
    public ResponseEntity<String> handelShelterNotFoundException(ShelterNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Приют не найден");
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handelConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bad param requested");
    }
}
