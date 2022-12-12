package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Cynologist;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
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

    @Operation(
            summary = "add a cynologist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds cynologist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cynologist.class))
                            )
                    )
            },
            tags = "Cynologist"
    )
    @PostMapping
    public ResponseEntity<Cynologist> createCynologist(@RequestBody Cynologist cynologist) {
        Cynologist newCynologist = cynologistService.createCynologist(cynologist);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "modify existing cynologist",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "modifies cynologist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cynologist.class))
                            )
                    )
            },
            tags = "Cynologist"
    )
    @PutMapping
    public ResponseEntity<Cynologist> updateCynologist(@RequestBody Cynologist cynologist) {
        Cynologist updateCynologist = cynologistService.updateCynologist(cynologist);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "searching for a certain cynologist by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "finds cynologist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cynologist.class))
                            )
                    )
            },
            tags = "Cynologist"
    )
    @GetMapping("{id}")
    public ResponseEntity<Cynologist> getCynologist(@PathVariable Long id) {
        Cynologist cynologist = cynologistService.getCynologist(id);
        if (cynologist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "delete the cynologist by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "deletes cynologist",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Cynologist.class))
                            )
                    )
            },
            tags = "Cynologist"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Cynologist> deleteCynologist(@PathVariable Long id) {
        cynologistService.deleteCynologist(id);
        return ResponseEntity.ok().build();
    }
}
