package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Shelter;
import ru.skypro.jd6.team3.animalshelter.exception.ShelterNotFoundException;
import ru.skypro.jd6.team3.animalshelter.repository.ShelterRepository;

import java.util.Optional;

@Service
public class ShelterService {

    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    /**
     * Получить местоположение приюта
     *
     */
    public String getShelterLocation(){
        logger.info("*getShelterLocation* method was invoked");
        return shelterRepository.getShelterLocation();
    }

    /**
     * Получить информацию о приюте
     *
     */
    public String getShelterDescription(){
        logger.info("*getShelterDescription* method was invoked");
        return shelterRepository.getShelterDescription();
    }

    /**
     * Получить адрес электронной почты приюта
     *
     */
    public String getShelterEmail(){
        logger.info("*getShelterEmail* method was invoked");
        return shelterRepository.getShelterEmail();
    }

    /**
     * Получает Приют
     * @param id параметр для поиска конкретного приюта
     *
     */
    public Shelter getShelter(Long id) {
        logger.info("*getShelter* method was invoked");
        Optional<Shelter> one = shelterRepository.findById(id);
        if (one.isPresent()) {
            Shelter shelterGet = one.get();
            logger.debug("Shelter by id: {}", id, one);
            return shelterGet;
        } else {
            logger.error("Student by id not found:{}", ShelterNotFoundException.class);
            return null;
        }
    }

}
