package ru.skypro.jd6.team3.animalshelter.service;

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.exception.OwnerNotFoundException;
import ru.skypro.jd6.team3.animalshelter.repository.OwnerRepository;
import ru.skypro.jd6.team3.animalshelter.repository.PetRepository;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Сервис для работы с классом Хозяин
 */
@Service
public class OwnerService {

// Закомментировал, потому что иначе Spring не запускается - а) не может создать Bean; б) Ругается на логгер
//    private final Logger logger = (Logger) LoggerFactory.getLogger(OwnerService.class);

    private final OwnerRepository ownerRepository;

    private final PetRepository petRepository;

    private final Pattern numberChecker = Pattern.compile("^(\\+7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$");

    public OwnerService(OwnerRepository ownerRepository,
                        PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    /**
     * Создает нового Хозяина в базе данных
     *
     * @return возвращает сущность добавленную в базу данных
     */
    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    /**
     * Получает Хозяин из баззы данных
     *
     * @param id параметр для поиска Хозяин в базе данных
     * @return возвращает Хозяин из базы данных если он есть, если его нет то выдаёт ошибку
     */
    public Owner getOwnerById(Long id) {
        Optional<Owner> one = ownerRepository.findById(id);
        if (one.isPresent()) {
            Owner ownerGet = one.get();
            return ownerGet;
        } else {
            return null;
        }
    }

    /**
     * Обновляет Хозяин из базы данных
     *
     * @return возвращает обновленную сущность Хозяин для базы данных
     */
    public Owner updateOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    /**
     * Удаляет Хозяин из базы данных
     *
     * @param id используется для поиска в базе данных
     */
    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
