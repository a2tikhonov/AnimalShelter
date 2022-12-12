package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.service.NewUserMenuService;

import java.util.Collection;

@RestController
@RequestMapping("/newUserMenu")
public class NewUserMenuController {

    private final NewUserMenuService newUserMenuService;

    public NewUserMenuController(NewUserMenuService newUserMenuService) {
        this.newUserMenuService = newUserMenuService;
    }

    @Operation(
            summary = "add menu button for new user menu",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "adds menu button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NewUserMenuButton.class))
                            )
                    )
            },
            tags = "NewUserMenuButton"
    )
    @PutMapping
    public ResponseEntity<NewUserMenuButton> create(@RequestBody NewUserMenuButton newUserMenuButton) {
        NewUserMenuButton newUserMenuButton1 = newUserMenuService.add(newUserMenuButton);
        return ResponseEntity.ok(newUserMenuButton1);
    }

    @Operation(
            summary = "get information about the certain button (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about the certain button",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NewUserMenuButton.class))
                            )
                    )
            },
            tags = "NewUserMenuButton"
    )
    @GetMapping("/{id}")
    public ResponseEntity<NewUserMenuButton> get(@PathVariable Long id) {
        NewUserMenuButton newUserMenuButton = newUserMenuService.get(id);
        return ResponseEntity.ok(newUserMenuButton);
    }

    @Operation(
            summary = "get information about all the buttons (both name and call_back)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get information about all the buttons",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = NewUserMenuButton.class))
                            )
                    )
            },
            tags = "NewUserMenuButton"
    )
    @GetMapping()
    public ResponseEntity<Collection<NewUserMenuButton>> getAll() {
        Collection<NewUserMenuButton> buttons = newUserMenuService.getButtons();
        return ResponseEntity.ok(buttons);
    }
}
