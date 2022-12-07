package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwnerMenuButton;

@Repository
public interface PotentialOwnerMenuRepository extends JpaRepository<PotentialOwnerMenuButton, Long> {

    Boolean existsByButton(String button);

    PotentialOwnerMenuButton findByButton(String button);

}
