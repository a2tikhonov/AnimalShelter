package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.repository.ReportRepository;

import java.io.*;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

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
     *
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

    public void uploadReportPhoto(Update update, Report report)  {
        for (PhotoSize photoSize : update.message().photo()) {
            try {
                GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
                byte[] fileBytes = telegramBot.getFileContent(getFileResponse.file());
                String mediaType = getFileResponse.file().filePath().substring(getFileResponse.file().filePath().lastIndexOf('.'));
                report.setPhoto(fileBytes);
                report.setMediaType(mediaType);
                report.setFileSize(getFileResponse.file().fileSize());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

/*        Report report = findReport(id);
        report.setFilePath(filePath.toString());
        report.setFileSize(reportFile.getSize());
        report.setMediaType(reportFile.getContentType());
        report.setPhoto(reportFile.getBytes());
        reportRepository.save(report);*/
    }

    public void sendReport(Update update, PotentialOwner potentialOwner) {
        for (PhotoSize photoSize : update.message().photo()) {
            try {
                GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
                byte[] fileBytes = telegramBot.getFileContent(getFileResponse.file());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}