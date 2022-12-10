package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.jd6.team3.animalshelter.entity.Location;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.entity.Shelter;
import ru.skypro.jd6.team3.animalshelter.repository.ReportRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class ReportService {

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    private final ReportRepository reportRepository;

    @Value("${upload.path}$")
    private String uploadPath;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /**
     * Добавление отчета
     *
     * @param report новый объект "Отчет"
     */
    public Report addReport(Report report) {
        logger.info("*addReport* method was invoked");
        return reportRepository.save(report);
    }

    /**
     * Поиск отчета
     *
     * @param id идентификатор отчета
     */
    public Report findReport(Long id) {
        logger.info("*findReport* method was invoked");
        return reportRepository.findById(id).orElse(null);
    }

    /**
     * Изменение отчета
     *
     * @param report новый объект "Отчет"
     */
    public Report editReport(Report report) {
        logger.info("*editReport* method was invoked");
        return reportRepository.save(report);
    }

    /**
     * Удаление отчета
     *
     * @param id идентификатор отчета
     */
    public void deleteReport(Long id) {
        logger.info("*deleteReport* method was invoked");
        logger.debug("We deleted report {}", reportRepository.findById(id));
        reportRepository.deleteById(id);
    }

    /**
     * Поиск всех отчетов
     *
     */
    public Collection<Report> findAll() {
        logger.info("*findAll* (reports) method was invoked");
        return reportRepository.findAll();
    }

    public Collection<Report> findOwnerReportById(Long id) {
        logger.debug("*findOwnerReportById* method was invoked");
        return reportRepository.findOwnerReportByOwnerId(id);
    }

    public Collection<Report> findOwnerReportByPhoneNumber(Long phone) {
        logger.debug("*findOwnerReportByPhoneNumber* method was invoked");
        return reportRepository.findOwnerReportByPhoneNumber(phone);
    }

    public Collection<Report> findOwnerReportByPetName(String name){
        logger.debug("*findOwnerReportByPetName* method was invoked");
        return reportRepository.findOwnerReportByPetIgnoreCase(name);
    }

    public Collection<Report> findAllReports() {
        logger.debug("*findAllReports* method was invoked");
        return reportRepository.findAll();
    }

//    public void uploadReportPhoto(Long reportId, MultipartFile reportFile) throws IOException {
//        Report report = findReport(reportId);
//
//        Path filePath = Path.of(uploadPath, reportId + "." + getExtension(reportFile.getOriginalFilename()));
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//
//        try (
//                InputStream is = reportFile.getInputStream();
//                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//
//        report.setFilePath(filePath.toString());
//        report.setFileSize(reportFile.getSize());
//        report.setMediaType(reportFile.getContentType());
//        report.setPhoto(reportFile.getBytes());
//
//        reportRepository.save(report);
//    }
//
//    private String getExtension(String fileName) {
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }
}
