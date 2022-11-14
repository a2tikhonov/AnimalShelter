package ru.skypro.jd6.team3.animalshelter.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.component.RecordComponent;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.PetNotFoundException;
import ru.skypro.jd6.team3.animalshelter.record.OwnerRecord;
import ru.skypro.jd6.team3.animalshelter.repository.OwnerRepository;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OwnerService {

    private final Logger logger = (Logger) LoggerFactory.getLogger(OwnerService.class);

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    private final RecordComponent recordComponent;

    public OwnerService(OwnerRepository ownerRepository,
                        PetRepository petRepository,
                        RecordComponent recordComponent) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.recordComponent = recordComponent;
    }

    public OwnerRecord createOwner(OwnerRecord ownerRecord) {
        Owner owner = recordComponent.toEntityOwnerRecord(ownerRecord);
        if (ownerRecord.getPet() != null) {
            Pet pet = petRepository.findById(ownerRecord.getPet().getId()).orElseThrow(PetNotFoundException::new);
        }
        return recordComponent.toRecordOwner(ownerRepository.save(owner));
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

    public OwnerRecord updateOwner(OwnerRecord ownerRecord) {
        Owner oldOwner = ownerRepository.findById(ownerRecord.getId())
                .orElseThrow(OwnerNotFoundException::new);
        oldOwner.setEmail(ownerRecord.getEmail());
        oldOwner.setName(ownerRecord.getName());
        oldOwner.setPhoneNumber(ownerRecord.getNumber());
        return recordComponent.toRecordOwner(ownerRepository.save(oldOwner));
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
