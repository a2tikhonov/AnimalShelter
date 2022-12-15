package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.service.ReportService;

import java.util.Collection;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Поиск отчета по идентификатору
     *
     * @param id - идентификатор отчета
     */
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
}
