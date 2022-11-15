package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Shelter;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    /**
     * Получить местоположение приюта (SQL-запрос, впоследствии используется в классе ShelterService)
     *
     */
    @Query(value = "SELECT shelter.location from shelter", nativeQuery = true)
    String getShelterLocation();

    /**
     * Получить информацию о приюте (SQL-запрос, впоследствии используется в классе ShelterService)
     *
     */
    @Query(value = "SELECT shelter.description from shelter", nativeQuery = true)
    String getShelterDescription();

    /**
     * Получить адрес электронной почты приюта (SQL-запрос, впоследствии используется в классе ShelterService)
     *
     */
    @Query(value = "SELECT shelter.email from shelter", nativeQuery = true)
    String getShelterEmail();

}

