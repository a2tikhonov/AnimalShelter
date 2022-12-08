package ru.skypro.jd6.team3.animalshelter.service;

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Collection;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet add(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet get(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public void remove(Long id) {
        petRepository.deleteById(id);
    }

    public Collection<Pet> getAll() {
        return petRepository.findAll();
    }
}
