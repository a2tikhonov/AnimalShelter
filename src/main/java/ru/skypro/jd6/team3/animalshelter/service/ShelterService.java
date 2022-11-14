package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.repository.ShelterRepository;

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

}
