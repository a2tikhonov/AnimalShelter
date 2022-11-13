package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.repository.OwnerRepository;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OwnerService {

    private final Logger logger = (Logger) LoggerFactory.getLogger(OwnerService.class);

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    public OwnerService(OwnerRepository ownerRepository,
                        PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner getOwnerById(Long id) {
        Optional<Owner> one = ownerRepository.findById(id);
        if (one.isPresent()) {
            Owner ownerGet = one.get();
            return ownerGet;
        } else {
            return null;
        }
    }

    public Owner updateOwner(Owner owner) {
        Owner oldOwner = ownerRepository.findById(owner.getOwnerId())
                .orElseThrow(OwnerNotFoundException::new);
        oldOwner.setEmail(owner.getEmail());
        oldOwner.setName(owner.getName());
        oldOwner.setPets(owner.getPets());
        oldOwner.setPhoneNumber(owner.getPhoneNumber());
        return ownerRepository.save(oldOwner);
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
