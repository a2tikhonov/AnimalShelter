package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonCat;

@Repository
public interface MainMenuCatRepository extends JpaRepository<MainMenuButtonCat, Long> {
    Boolean existsByButton(String button);

    MainMenuButtonCat findByButton(String button);
}
