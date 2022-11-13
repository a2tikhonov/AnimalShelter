package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.PetNotFoundException;
import ru.skypro.jd6.team3.animalshelter.repository.OwnerRepository;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PetService {
    private final Logger logger = (Logger) LoggerFactory.getLogger(PetService.class);

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    public PetService(OwnerRepository ownerRepository,
                      PetRepository petRepository) {
        this.petRepository = petRepository;
        this.ownerRepository =ownerRepository;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        Optional<Pet> one = petRepository.findById(id);
        if (one.isPresent()) {
            Pet petGet = one.get();
            return petGet;
        } else {
            return null;
        }
    }

    public Pet updatePet(Pet pet) {
        Pet oldPet = petRepository.findById(pet.getPetId())
                .orElseThrow(PetNotFoundException::new);
        oldPet.setAge(pet.getAge());
        oldPet.setName(pet.getName());
        oldPet.setBreed(pet.getBreed());
        oldPet.setWeight(pet.getWeight());
        return petRepository.save(oldPet);
    }

    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
