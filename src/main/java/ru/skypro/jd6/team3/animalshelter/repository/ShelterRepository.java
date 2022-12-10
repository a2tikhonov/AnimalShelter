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
    @Query(value = "SELECT shelters.location from shelters WHERE shelters.id = id", nativeQuery = true)
    String getShelterLocation(id);

    /**
     * Получить информацию о приюте (SQL-запрос, впоследствии используется в классе ShelterService)
     *
     */
    @Query(value = "SELECT shelters.description from shelters WHERE shelters.id = id", nativeQuery = true)
    String getShelterDescription(id);

    /**
     * Получить адрес электронной почты приюта (SQL-запрос, впоследствии используется в классе ShelterService)
     *
     */
    @Query(value = "SELECT shelters.email from shelters WHERE shelters.id = id", nativeQuery = true)
    String getShelterEmail(id);

}

