package ru.skypro.jd6.team3.animalshelter.service;
/**
 * Сервис для работы с классом Питомец
 */

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.exception.PetNotFoundException;
import ru.skypro.jd6.team3.animalshelter.repository.OwnerRepository;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Optional;

@Service
public class PetService {

// Закомментировал, потому что иначе Spring не запускается - а) не может создать Bean; б) Ругается на логгер
//    private final Logger logger = (Logger) LoggerFactory.getLogger(PetService.class);

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    public PetService(OwnerRepository ownerRepository,
                      PetRepository petRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    /**
     * Создаёт нового Питомца в базе данных
     *
     * @return возвращает сущность добавленную в базу данных
     */
    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
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
     *
     * @return возвращает обновленную сущность Питомец для базы данных
     */
    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }
    /**
     * Удаляет Питомец из базы данных
     * @param id используется для поиска в базе данных
     */
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
}
