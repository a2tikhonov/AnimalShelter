package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.InfoMenu;

import java.util.Collection;

@Repository
public interface InfoMenuRepository extends JpaRepository<InfoMenu, Long> {

    @Query(value = "SELECT InfoMenu.button FROM InfoMenu", nativeQuery = true)
    Collection<String> getAllButtons();

    InfoMenu findByButton(String button);

}
