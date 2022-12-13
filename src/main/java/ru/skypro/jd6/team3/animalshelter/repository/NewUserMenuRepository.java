package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;

public interface NewUserMenuRepository extends JpaRepository<NewUserMenuButton, Long> {
    Boolean existsByButton(String button);

    NewUserMenuButton findByButton(String button);

}
