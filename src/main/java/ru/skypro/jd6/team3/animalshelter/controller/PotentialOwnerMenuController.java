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
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwnerMenuButton;
import ru.skypro.jd6.team3.animalshelter.service.PotentialOwnerMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/consultMenu")
public class PotentialOwnerMenuController {

    private final PotentialOwnerMenuService potentialOwnerMenuService;

    public PotentialOwnerMenuController(PotentialOwnerMenuService potentialOwnerMenuService) {
        this.potentialOwnerMenuService = potentialOwnerMenuService;
    }

    @PostMapping
    public ResponseEntity<PotentialOwnerMenuButton> create(@RequestBody PotentialOwnerMenuButton potentialOwnerMenuButton) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(potentialOwnerMenuService.add(potentialOwnerMenuButton));
    }


    @Operation(
            summary = "add menu button for potential owner",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds menu button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PotentialOwnerMenuButton.class))
                            )
                    )
            },
            tags = "PotentialOwnerMenuButton"
    )
    @PutMapping
    public ResponseEntity<PotentialOwnerMenuButton> create(@RequestBody PotentialOwnerMenuButton potentialOwnerMenuButton) {
        PotentialOwnerMenuButton potentialOwnerMenuButton1 = potentialOwnerMenuService.add(potentialOwnerMenuButton);
        return ResponseEntity.ok(potentialOwnerMenuButton1);
    }

    @Operation(
            summary = "get information about all the buttons (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about all the buttons",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PotentialOwnerMenuButton.class))
                            )
                    )
            },
            tags = "PotentialOwnerMenuButton"
    )
    @GetMapping
    public ResponseEntity<Collection<PotentialOwnerMenuButton>> getAll() {
        return ResponseEntity.ok(potentialOwnerMenuService.getButtons());
    }

    @Operation(
            summary = "get information about the certain button (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about the certain button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PotentialOwnerMenuButton.class))
                            )
                    )
            },
            tags = "PotentialOwnerMenuButton"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PotentialOwnerMenuButton> get(@PathVariable Long id) {
        PotentialOwnerMenuButton potentialOwnerMenuButton = potentialOwnerMenuService.get(id);
        return ResponseEntity.ok(potentialOwnerMenuButton);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        potentialOwnerMenuService.delete(id);
        return ResponseEntity.ok().build();
    }
}
