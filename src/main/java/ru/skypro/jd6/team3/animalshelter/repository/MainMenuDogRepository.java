package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonDog;

@Repository
public interface MainMenuDogRepository extends JpaRepository<MainMenuButtonDog, Long> {
    Boolean existsByButton(String button);

    MainMenuButtonDog findByButton(String button);
}
