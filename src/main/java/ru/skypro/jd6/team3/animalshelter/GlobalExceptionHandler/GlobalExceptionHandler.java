package ru.skypro.jd6.team3.animalshelter.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.PetNotFoundException;

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handelConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Bad param requested");
    }
}
