package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.repository.ReportRepository;

import java.util.Collection;

@Service
public class ReportService {

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /**
     * Добавление отчета
     *
     * @param report новый объект "Отчет"
     */
    public Report addReport(Report report) {
        logger.info("*addVolunteer* method was invoked");
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
}
