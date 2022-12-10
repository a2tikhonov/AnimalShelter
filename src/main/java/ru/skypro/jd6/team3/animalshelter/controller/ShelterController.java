package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.jd6.team3.animalshelter.entity.Shelter;
import ru.skypro.jd6.team3.animalshelter.service.ShelterService;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }


    @Operation(
            summary = "get information about the shelter",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check shelter location",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            },
            tags = "Shelter"
    )
    @GetMapping("/shelter-location")
    public String getShelterLocation() {
        return shelterService.getShelterLocation();
    }

    @Operation(
            summary = "get information about the shelter",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check shelter description",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            },
            tags = "Shelter"
    )
    @GetMapping("/shelter-description")
    public String getShelterDescription() {
        return shelterService.getShelterDescription();
    }

    @Operation(
            summary = "get information about the shelter",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Check shelter email",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            },
            tags = "Shelter"
    )
    @GetMapping("/shelter-email")
    public String getShelterEmail() {
        return shelterService.getShelterEmail();
    }
}
