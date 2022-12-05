package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;

import java.util.Collection;

@Repository
public interface MainMenuRepository extends JpaRepository<MainMenuButton, Long> {

    @Query(value = "SELECT main_menu.button FROM main_menu", nativeQuery = true)
    Collection<String> getAllButtons();

    MainMenuButton findByButton(String button);

}
