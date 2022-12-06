package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;

@Repository
public interface MainMenuRepository extends JpaRepository<MainMenuButton, Long> {
    Boolean existsByButton(String button);

    MainMenuButton findByButton(String button);

}
