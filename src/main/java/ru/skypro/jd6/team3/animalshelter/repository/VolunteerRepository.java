package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Volunteer findVolunteerByPotentialOwner_Id(Long id);

    Boolean existsByPotentialOwner(PotentialOwner potentialOwner);

    Volunteer getFirstByPotentialOwner(PotentialOwner potentialOwner);


}