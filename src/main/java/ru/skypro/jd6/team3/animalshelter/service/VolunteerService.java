package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.repository.VolunteerRepository;

import java.util.Collection;

@Service
public class VolunteerService {
    private final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Добавление волонтера
     *
     * @param volunteer новый объект "Волонтер"
     */
    public Volunteer addVolunteer(Volunteer volunteer) {
        logger.info("*addVolunteer* method was invoked");
        return volunteerRepository.save(volunteer);
    }

    /**
     * Поиск волонтера
     *
     * @param id идентификатор волонтера
     */
    public Volunteer findVolunteer(Long id) {
        logger.info("*findVolunteer* method was invoked");
        return volunteerRepository.findById(id).orElse(null);
    }

    /**
     * Замена волонтера
     *
     * @param volunteer новый объект "Волонтер"
     */
    public Volunteer editVolunteer(Volunteer volunteer) {
        logger.info("*editVolunteer* method was invoked");
        return volunteerRepository.save(volunteer);
    }

    /**
     * Удаление волонтера
     *
     * @param id идентификатор волонтера
     */
    public void deleteVolunteer(Long id) {
        logger.info("*deleteVolunteer* method was invoked");
        logger.debug("We deleted volunteer {}", volunteerRepository.findById(id));
        volunteerRepository.deleteById(id);
    }

    /**
     * Поиск всех волонтеров
     *
     */
    public Collection<Volunteer> findAll() {
        logger.info("*findAll* (volunteers) method was invoked");
        return volunteerRepository.findAll();
    }

}
