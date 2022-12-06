package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;

import java.util.Collection;

public interface NewUserMenuRepository extends JpaRepository<NewUserMenuButton, Long> {

    @Query(value = "SELECT new_user_menu.button FROM new_user_menu", nativeQuery = true)
    Collection<String> getAllButtons();

    NewUserMenuButton findByButton(String button);
}
