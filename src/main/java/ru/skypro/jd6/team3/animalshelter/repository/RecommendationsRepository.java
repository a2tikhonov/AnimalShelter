package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Recommendations;

@Repository
public interface RecommendationsRepository extends JpaRepository<Recommendations,Long> {

    /*
        public String rules;
    public String documents;
    public String transportationInformation;
    public String arrangementInformation;
    public String cynologistAdvices;
    public String listOfCynologists;
    public String commonRejectReasons;
     */
    /**
     * Получить перечень правил
     *
     */
    @Query(value = "SELECT recommendations.rules from recommendations", nativeQuery = true)
    String getRecommendationsRules();

    /**
     * Получить перечень документов
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
