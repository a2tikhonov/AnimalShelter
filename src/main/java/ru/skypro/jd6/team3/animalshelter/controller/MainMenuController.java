package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.service.MainMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/mainMenu")
public class MainMenuController {

    private final MainMenuService mainMenuService;

    public MainMenuController(MainMenuService mainMenuService) {
        this.mainMenuService = mainMenuService;
    }

    @Operation(
            summary = "add menu button for main menu",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds menu button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MainMenuButton.class))
                            )
                    )
            },
            tags = "MainMenuButton"
    )
    @PutMapping
    public ResponseEntity<MainMenuButton> create(@RequestBody MainMenuButton mainMenuButton) {
        MainMenuButton mainMenuButton1 = mainMenuService.add(mainMenuButton);
        return ResponseEntity.ok(mainMenuButton1);
    }

    @Operation(
            summary = "get information about all the buttons (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about all the buttons",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MainMenuButton.class))
                            )
                    )
            },
            tags = "MainMenuButton"
    )
    @GetMapping
    public ResponseEntity<Collection<MainMenuButton>> getAll() {
        return ResponseEntity.ok(mainMenuService.getButtons());
    }

    @Operation(
            summary = "get information about the certain button (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about the certain button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = MainMenuButton.class))
                            )
                    )
            },
            tags = "MainMenuButton"
    )
    @GetMapping("/{id}")
    public ResponseEntity<MainMenuButton> get(@PathVariable Long id) {
        MainMenuButton mainMenuButton = mainMenuService.get(id);
        return ResponseEntity.ok(mainMenuButton);
    }

}
