package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.repository.ReportRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    private final ReportRepository reportRepository;

    private final TelegramBot telegramBot;


    public ReportService(ReportRepository repository, TelegramBot telegramBot) {
        this.reportRepository = repository;
        this.telegramBot = telegramBot;
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
     */
    public Collection<Report> findAll() {
        logger.info("*findAll* (reports) method was invoked");
        return reportRepository.findAll();
    }

//    public Collection<Report> findOwnerReportById(Long id) {
//        logger.debug("*findOwnerReportById* method was invoked");
//        return reportRepository.findOwnerReportByOwnerId(id);
//    }

//    public Collection<Report> findOwnerReportByPhoneNumber(Long phone) {
//        logger.debug("*findOwnerReportByPhoneNumber* method was invoked");
//        return reportRepository.findOwnerReportByPhoneNumber(phone);
//    }
//
//    public Collection<Report> findOwnerReportByPetName(String name){
//        logger.debug("*findOwnerReportByPetName* method was invoked");
//        return reportRepository.findOwnerReportByPetIgnoreCase(name);
//    }

    public Collection<Report> findAllReports() {
        logger.debug("*findAllReports* method was invoked");
        return reportRepository.findAll();
    }

    public Report getLastBy(PotentialOwner potentialOwner) {
        Optional<Report> report = Optional.of(reportRepository.findTopByPotentialOwnerOrderByIdDesc(potentialOwner));
        return report.orElse(null);
    }

    public void uploadReportPhoto(PhotoSize[] photoSizes, PotentialOwner potentialOwner) {
        String mediaType = null;
        Long fileSize = 0L;
        String photoId =null;
        for (PhotoSize photoSize : photoSizes) {
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
            mediaType = "image/" + getFileResponse.file().filePath()
                    .substring(getFileResponse.file().filePath().lastIndexOf('.') + 1);
            fileSize = getFileResponse.file().fileSize();
            photoId = photoSize.fileId();
        }
        Report report = updateOrCreateReport(potentialOwner);
        report.setPhotoId(photoId);
        report.setMediaType(mediaType);
        report.setFileSize(fileSize);
        reportRepository.save(report);
    }

    public void uploadReportText(String text, PotentialOwner potentialOwner) {
        Report report = updateOrCreateReport(potentialOwner);
        report.setReportText(text);
        reportRepository.save(report);
    }

    private Report updateOrCreateReport(PotentialOwner potentialOwner) {
        Report report;
        LocalDate day = LocalDate.now();
        if (existsBy(potentialOwner)) {
            report = getLastBy(potentialOwner);
        } else {
            report = new Report();
            report.setOwner(potentialOwner);
        }
        report.setDayOfMonth(day);
        return report;
    }

    public Boolean reportCompleted(PotentialOwner potentialOwner) {
        if (existsBy(potentialOwner)) {
            Report report = getLastBy(potentialOwner);
            if (report.getReportText() != null && report.getPhotoId() != null) {
                return true;
            }
        }
        return false;
    }

    public Boolean existsBy(PotentialOwner potentialOwner) {
        return reportRepository.existsByPotentialOwner(potentialOwner);
    }

    public byte[] downloadPhoto(Long reportId) {
        byte[] fileBytes = null;
        Report report = reportRepository.findById(reportId).orElse(null);
        if (report != null) {
            try {
                GetFileResponse getFileResponse = telegramBot.execute(new GetFile(report.getPhotoId()));
                fileBytes = telegramBot.getFileContent(getFileResponse.file());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return fileBytes;
    }

    public String getReportForm() {
        return "В ежедневный отчет входит следующая информация:\n" +
                "Фото животного;\n" +
                "Рацион животного;\n" +
                "Общее самочувствие и привыкание к новому месту;\n" +
                "Изменение в поведении: отказ от старых привычек, приобретение новых.";
    }

    public List<Long> reportTimer() {
        LocalDate currentDay = LocalDate.now();
        Collection<Report> outdatedReports = reportRepository.findAllByDayOfMonthBefore(currentDay);
        List<Long> userIdsWithOutdatedReports = outdatedReports.stream().map(e -> e.getOwner().getId()).collect(Collectors.toList());
        return userIdsWithOutdatedReports;
    }


}