package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.service.PotentialOwnerService;

import java.util.Collection;

@RestController
@RequestMapping("/potential_owner")
public class PotentialOwnerController {

    PotentialOwnerService potentialOwnerService;

    public PotentialOwnerController(PotentialOwnerService potentialOwnerService) {
        this.potentialOwnerService = potentialOwnerService;
    }

    @Operation(
            summary = "add a Potential Owner",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds potential owner",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PotentialOwner.class))
                            )
                    )
            },
            tags = "PotentialOwner"
    )
    @PutMapping
    public ResponseEntity<PotentialOwner> create(@RequestBody PotentialOwner potentialOwner) {
        PotentialOwner potentialOwner1 = potentialOwnerService.save(potentialOwner);
        return ResponseEntity.ok(potentialOwner1);
    }

    @Operation(
            summary = "get list of all potential owners",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "gets a list for all potential owners",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PotentialOwner.class))
                            )
                    )
            },
            tags = "PotentialOwner"
    )
    @GetMapping
    public ResponseEntity<Collection<PotentialOwner>> getAll() {
        return ResponseEntity.ok(potentialOwnerService.getAll());
    }

    @Operation(
            summary = "search for the certain potential owner by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "gets a potential owner by id",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PotentialOwner.class))
                            )
                    )
            },
            tags = "PotentialOwner"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PotentialOwner> get(@PathVariable Long id) {
        PotentialOwner potentialOwner = potentialOwnerService.get(id);
        return ResponseEntity.ok(potentialOwner);
    }
}
