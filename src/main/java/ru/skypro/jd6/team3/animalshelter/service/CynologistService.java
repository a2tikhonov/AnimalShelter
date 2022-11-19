package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Cynologist;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.repository.CynologistRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class CynologistService {

    private final Logger logger = LoggerFactory.getLogger(CynologistService.class);
    private final CynologistRepository cynologistRepository;

    public CynologistService(CynologistRepository cynologistRepository) {
        this.cynologistRepository = cynologistRepository;
    }

    /**
     * Создаёт Кинолога
     *
     * @return новый объект "Кинолог"
     */
    public Cynologist createCynologist(Cynologist cynologist) {
        logger.info("*createCynologist* method was invoked");
        return cynologistRepository.save(cynologist);
    }

    /**
     * Поиск кинолога
     * @param id параметр по которому ищем кинолога в базе данных
     *
     */
    public Cynologist getCynologist(Long id) {
        logger.info("*getCynologist* method was invoked");
        Optional<Cynologist> one = cynologistRepository.findById(id);
        if (one.isPresent()) {
            Cynologist cynologistGet = one.get();
            return cynologistGet;
        } else {
            return null;
        }
    }

    /**
     * Обновление кинолога
     * @param cynologist новый объект "Кинолог"
     */
    public Cynologist updateCynologist(Cynologist cynologist) {
        logger.info("*updateCynologist* method was invoked");
        return cynologistRepository.save(cynologist);
    }

    /**
     * Удаление Кинолога
     * @param id параметр по которому ищем кинолога в базе данных и удаляем его.
     */
    public void deleteCynologist(Long id) {
        logger.info("*deleteCynologist* method was invoked");
        logger.debug("We deleted cynologist {}", cynologistRepository.findById(id));
        cynologistRepository.deleteById(id);
    }

    /**
     * Поиск всех Кинологов
     *
     */
    public Collection<Cynologist> listOfCynologist() {
        logger.info("*listOfCynologist* method was invoked");
        return cynologistRepository.findAll();
    }
}
