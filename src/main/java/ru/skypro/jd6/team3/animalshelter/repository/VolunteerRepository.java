package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;

import java.util.Collection;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Collection<Volunteer> getAllByBusy(Boolean busy);

    Volunteer getFirstByBusy(Boolean busy);
}
