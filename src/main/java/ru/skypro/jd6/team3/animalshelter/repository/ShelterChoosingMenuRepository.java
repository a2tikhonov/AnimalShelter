package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.ShelterChoosingMenu;

@Repository
public interface ShelterChoosingMenuRepository extends JpaRepository<ShelterChoosingMenu, Long> {
    Boolean existsByButton(String button);

    ShelterChoosingMenu findByButton(String button);
}
