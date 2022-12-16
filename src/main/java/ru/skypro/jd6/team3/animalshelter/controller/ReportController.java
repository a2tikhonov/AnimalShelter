package ru.skypro.jd6.team3.animalshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.entity.Shelter;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.service.ReportService;

import java.util.Collection;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "search for report",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "finds certain report",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                            )
                    )
            },
            tags = "Report"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Report> findReport(@PathVariable Long id) {
        Report report = reportService.findReport(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }

    /**
     * Поиск всех отчетов
     */
    @GetMapping
    public ResponseEntity<Collection<Report>> findReports() {
        return ResponseEntity.ok(reportService.findAll());
    }

    /**
     * Добавление отчета через браузер
     *
     * @param report новый объект "Волонтер"
     */
    @PostMapping
    public ResponseEntity<Report> addReport(@RequestBody Report report) {
        Report newReport = reportService.addReport(report);
        return ResponseEntity.ok(newReport);
    }

    /**
     * Изменение отчета через браузер
     *
     * @param report новый объект "Волонтер"
     */
    @PutMapping
    public ResponseEntity<Report> editReport(@RequestBody Report report) {
        Report updateReport = reportService.editReport(report);
        return ResponseEntity.ok(updateReport);
    }

    /**
     * Удалить отчет по идентификатору
     *
     * @param id - идентификатор отчета
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportService.deleteReport(id);
        return ResponseEntity.ok().build();
    }

//    @Operation(
//            summary = "search for the certain report or find all of them",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "finds certain report or all of 'em",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
//                            )
//                    )
//            },
//            tags = "Report"
//    )
////    @GetMapping
////    public ResponseEntity<Collection<Report>> findCertainReport(
////            @RequestParam(required = false) Long id,
////            @Parameter(description = "find report by pet's name", example = "Balto") @RequestParam(required = false) String name,
////            @Parameter(description = "find report by owner's phoneNumber", example = "891688253040")@RequestParam(required = false) Long phoneNumber) {
////        if (id != null) {
////            return ResponseEntity.ok(reportService.findOwnerReportById(id));
////        }
////        if (name != null && !name.isBlank()) {
////            return ResponseEntity.ok(reportService.findOwnerReportByPetName(name));
////        }
////        if (phoneNumber != null && phoneNumber != 0) {
////            return ResponseEntity.ok(reportService.findOwnerReportByPhoneNumber(phoneNumber));
////        }
////        return ResponseEntity.ok(reportService.findAll());
////    }

}
