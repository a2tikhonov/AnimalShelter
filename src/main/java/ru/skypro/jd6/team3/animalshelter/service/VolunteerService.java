package ru.skypro.jd6.team3.animalshelter.service;

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Volunteer;
import ru.skypro.jd6.team3.animalshelter.repository.VolunteerRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer save(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer get(Long id) {
        return volunteerRepository.findById(id).orElse(null);
    }

    public boolean find(Long id) {
        return volunteerRepository.findById(id).isPresent();
    }

    public Collection<Volunteer> get() {
        return volunteerRepository.findAll();
    }

    public void remove(Long id) {
        volunteerRepository.deleteById(id);
    }

    public Volunteer getFree() {
        return Optional.ofNullable(volunteerRepository.getFirstByBusy(false)).orElse(null);
    }
}
