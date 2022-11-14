package ru.skypro.jd6.team3.animalshelter.service;
/**
 * Сервис для работы с классом Питомец
 */

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.component.RecordComponent;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.exception.PetNotFoundException;
import ru.skypro.jd6.team3.animalshelter.record.PetRecord;
import ru.skypro.jd6.team3.animalshelter.repository.OwnerRepository;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PetService {

// Закомментировал, потому что иначе Spring не запускается - а) не может создать Bean; б) Ругается на логгер
//    private final Logger logger = (Logger) LoggerFactory.getLogger(PetService.class);

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;
    private final RecordComponent recordComponent;

    public PetService(OwnerRepository ownerRepository,
                      PetRepository petRepository,
                      RecordComponent recordComponent) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.recordComponent = recordComponent;
    }

    /**
     * Создаёт нового Питомца в базе данных
     * @param petRecord принимает рекорд Питомец
     * @return возвращает сущность добавленную в базу данных
     */
    public PetRecord createPet(PetRecord petRecord) {
        Pet pet = recordComponent.toEntityPetRecord(petRecord);
        if (petRecord.getOwner() != null) {
            Owner owner = ownerRepository.findById(petRecord.getOwner().getId()).orElseThrow(OwnerNotFoundException::new);
            pet.setOwner(owner);
        }
        return recordComponent.toRecordPet(petRepository.save(pet));
    }
    /**
     * Получает Питомец из баззы данных
     * @param id параметр для поиска Питомец в базе данных
     * @return возвращает Питомец из базы данных если он есть, если его нет то выдаёт ошибку
     */
    public Pet getPet(Long id) {
        Optional<Pet> one = petRepository.findById(id);
        if (one.isPresent()) {
            Pet petGet = one.get();
            return petGet;
        } else {
            return null;
        }
    }

    /**
     * Обновляет Питомец из базы данных
     * @param petRecord этот рекорд содержит данные для поиск и обновления
     * @return возвращает обновленную сущность Питомец для базы данных
     */
    public PetRecord updatePet(PetRecord petRecord) {
        Pet oldPet = petRepository.findById(petRecord.getId())
                .orElseThrow(PetNotFoundException::new);
        oldPet.setAge(petRecord.getAge());
        oldPet.setName(petRecord.getName());
        oldPet.setBreed(petRecord.getBreed());
        oldPet.setWeight(petRecord.getWeight());
        return recordComponent.toRecordPet(petRepository.save(oldPet));
    }
    /**
     * Удаляет Питомец из базы данных
     * @param id используется для поиска в базе данных
     */
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
