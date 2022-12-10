package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Report;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findOwnerReportByPetIgnoreCase(String name);
    List<Report> findOwnerReportByPhoneNumber(long phone);
    List<Report> findOwnerReportByOwnerId(Long id);
}
