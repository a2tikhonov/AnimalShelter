package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {

    Boolean existsPetByPotentialOwner(PotentialOwner potentialOwner);
}