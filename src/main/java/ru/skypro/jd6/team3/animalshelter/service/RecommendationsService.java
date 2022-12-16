package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Recommendations;
import ru.skypro.jd6.team3.animalshelter.entity.Report;
import ru.skypro.jd6.team3.animalshelter.repository.RecommendationsRepository;
//import ru.skypro.jd6.team3.animalshelter.repository.ReportRepository;

@Service
public class RecommendationsService {
    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    private final RecommendationsRepository recommendationsRepository;

    public RecommendationsService(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    /**
     * Добавление списка рекоммендаций
     *
     * @param recommendations новый объект "Рекоммендации"
     */
    public Recommendations addRecommendations(Recommendations recommendations) {
        logger.info("*addRecommendations* method was invoked");
        return recommendationsRepository.save(recommendations);
    }

    /**
     * Поиск рекоммендаций (полный список)
     *
     * @param id идентификатор списка рекоммендаций
     */
    public Recommendations findRecommendations(Long id) {
        logger.info("*findRecommendations* method was invoked");
        return recommendationsRepository.findById(id).orElse(null);
    }

    /**
     * Внесение изменений в рекоммендации (целиком)
     *
     * @param recommendations новый объект "Рекоммендации"
     */
    public Recommendations editReport(Recommendations recommendations) {
        logger.info("*editRecommendations* method was invoked");
        return recommendationsRepository.save(recommendations);
    }
}




