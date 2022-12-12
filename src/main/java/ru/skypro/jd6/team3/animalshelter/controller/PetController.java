package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.record.PetRecord;
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

    @Operation(
            summary = "add a pet",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds pet",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            },
            tags = "Pet"
    )
    @PostMapping
    public ResponseEntity<PetRecord> createPet(@RequestBody PetRecord pet) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(petService.createPet(pet));
    }

    @Operation(
            summary = "searching for a certain pet by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "finds pet",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            },
            tags = "Pet"
    )
    @GetMapping("{id}")
    public ResponseEntity<Pet> getPet(@PathVariable Long id) {
        Pet pet = petService.getPet(id);
        if (pet == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "modify existing pet",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "modifies pet",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            },
            tags = "Pet"
    )
    @PutMapping
    public PetRecord updatePet(@RequestBody PetRecord petRecord) {
        return petService.updatePet(petRecord);
    }

    @Operation(
            summary = "delete the pet by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "deletes pet",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Pet.class))
                            )
                    )
            },
            tags = "Pet"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }
}
