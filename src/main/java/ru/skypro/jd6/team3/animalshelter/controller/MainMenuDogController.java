package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonDog;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuDogService;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/mainMenu")
public class MainMenuDogController {

    private final MainMenuDogService mainMenuDogService;

    public MainMenuDogController(MainMenuDogService mainMenuDogService) {
        this.mainMenuDogService = mainMenuDogService;
    }

    @Operation(
            summary = "add menu button for main menu",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds menu button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MainMenuButtonDog.class))
                            )
                    )
            },
            tags = "MainMenuButton"
    )
    @PutMapping
    public ResponseEntity<MainMenuButtonDog> create(@RequestBody MainMenuButtonDog mainMenuButtonDog) {
        MainMenuButtonDog mainMenuButtonDog1 = mainMenuDogService.add(mainMenuButtonDog);
        return ResponseEntity.ok(mainMenuButtonDog1);
    }

    @Operation(
            summary = "get information about all the buttons (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about all the buttons",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MainMenuButtonDog.class))
                            )
                    )
            },
            tags = "MainMenuButton"
    )
    @GetMapping
    public ResponseEntity<Collection<MainMenuButtonDog>> getAll() {
        return ResponseEntity.ok(mainMenuDogService.getButtons());
    }

    @Operation(
            summary = "get information about the certain button (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about the certain button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MainMenuButtonDog.class))
                            )
                    )
            },
            tags = "MainMenuButton"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MainMenuButtonDog> get(@PathVariable Long id) {
        MainMenuButtonDog mainMenuButtonDog = mainMenuDogService.get(id);
        return ResponseEntity.ok(mainMenuButtonDog);
    }

}
