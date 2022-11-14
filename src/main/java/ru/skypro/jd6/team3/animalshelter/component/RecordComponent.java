package ru.skypro.jd6.team3.animalshelter.component;

import org.springframework.stereotype.Component;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.record.OwnerRecord;
import ru.skypro.jd6.team3.animalshelter.record.PetRecord;

@Component
public class RecordComponent {

    public OwnerRecord toRecordOwner(Owner owner) {
        OwnerRecord ownerRecord = new OwnerRecord();
        ownerRecord.setId(owner.getOwnerId());
        ownerRecord.setName(owner.getName());
        ownerRecord.setEmail(owner.getEmail());
        ownerRecord.setNumber(owner.getPhoneNumber());
        return ownerRecord;
    }

    public Owner toEntityOwnerRecord(OwnerRecord ownerRecord) {
        Owner owner = new Owner();
        owner.setName(ownerRecord.getName());
        owner.setPhoneNumber(ownerRecord.getNumber());
        owner.setEmail(ownerRecord.getEmail());
        return owner;
    }


    public PetRecord toRecordPet(Pet pet) {
        PetRecord petRecord = new PetRecord();
        petRecord.setId(pet.getPetId());
        petRecord.setName(pet.getName());
        petRecord.setBreed(pet.getBreed());
        petRecord.setAge(pet.getAge());
        petRecord.setWeight(pet.getWeight());
        if (pet.getOwner() != null) {
            petRecord.setOwner(toRecordOwner(pet.getOwner()));
        }

        return petRecord;
    }

    public Pet toEntityPetRecord(PetRecord petRecord) {
        Pet pet = new Pet();
        pet.setName(petRecord.getName());
        pet.setWeight(petRecord.getWeight());
        pet.setBreed(petRecord.getBreed());
        pet.setAge(petRecord.getAge());
        return pet;
    }
}
