package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
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

    @Operation(
            summary = "add volunteer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds volunteer",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteer"
    )
    @PutMapping
    public ResponseEntity<Volunteer> add(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.save(volunteer));
    }

    @Operation(
            summary = "search volunteer",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "finds volunteer",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )
                    )
            },
            tags = "Volunteer"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> get(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.get(id));
    }

    @GetMapping
    public ResponseEntity<Collection<Volunteer>> get() {
        return ResponseEntity.ok(volunteerService.get());
    }
}
