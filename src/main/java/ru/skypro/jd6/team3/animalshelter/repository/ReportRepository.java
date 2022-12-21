package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Report;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

//    List<Report> findOwnerReportByPetIgnoreCase(String name);
//    List<Report> findOwnerReportByPhoneNumber(long phone);
//    List<Report> findOwnerReportByOwnerId(Long id);

    Boolean existsByPotentialOwner(PotentialOwner potentialOwner);

    Report findTopByPotentialOwnerOrderByIdDesc(PotentialOwner potentialOwner);

    Collection<Report> findAllByDayOfMonthBefore(LocalDate day);
}
