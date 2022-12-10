package ru.skypro.jd6.team3.animalshelter.component;

import org.springframework.stereotype.Component;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.record.OwnerRecord;
import ru.skypro.jd6.team3.animalshelter.record.PetRecord;

/**
 * Класс для работы с рекордами классов и с классами
 */
@Component
public class RecordComponent {
    /**
     * Превращает сущновсть Хозяин в рекорд Хоязин
     * @param owner прнимаемая сущность Хозяин
     * @return возвращаемый рекорд Хозяин
     */
    public OwnerRecord toRecordOwner(Owner owner) {
        OwnerRecord ownerRecord = new OwnerRecord();
        ownerRecord.setId(owner.getOwnerId());
        ownerRecord.setName(owner.getName());
        ownerRecord.setEmail(owner.getEmail());
        ownerRecord.setNumber(owner.getPhoneNumber());
        return ownerRecord;
    }

    /**
     * Превращает рекорд Хозяин в сущность Хозяин
     * @param ownerRecord принемаемый рекорд Хозяин
     * @return возвращаемая сущность Хозяин
     */
    public Owner toEntityOwnerRecord(OwnerRecord ownerRecord) {
        Owner owner = new Owner();
        owner.setName(ownerRecord.getName());
        owner.setPhoneNumber(ownerRecord.getNumber());
        owner.setEmail(ownerRecord.getEmail());
        return owner;
    }

    /**
     * Превращает сущновсть Питомец в рекорд Питомец
     * @param pet прнимаемая сущность Питомец
     * @return возвращаемый рекорд Питомец
     */
    public PetRecord toRecordPet(Pet pet) {
        PetRecord petRecord = new PetRecord();
        petRecord.setId(pet.getPetId());
        petRecord.setName(pet.getName());
        petRecord.setBreed(pet.getBreed());
        petRecord.setAge(pet.getAge());
        petRecord.setWeight(pet.getWeight());
        petRecord.setSpecies(pet.getSpecies());
        petRecord.setShelter(pet.getShelter());
        return petRecord;
    }

    /**
     * Превращает рекорд Питомец в сущновсть Питомец
     * @param petRecord принемаемый рекорд Питомец
     * @return возвращаемая сущность Питомец
     */
    public Pet toEntityPetRecord(PetRecord petRecord) {
        Pet pet = new Pet();
        pet.setName(petRecord.getName());
        pet.setWeight(petRecord.getWeight());
        pet.setBreed(petRecord.getBreed());
        pet.setAge(petRecord.getAge());
        pet.setSpecies(petRecord.getSpecies());
        pet.setShelter(petRecord.getShelter());
        return pet;
    }
}
