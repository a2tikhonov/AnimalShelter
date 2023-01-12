package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwner;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {

    Boolean existsPetByPotentialOwner(PotentialOwner potentialOwner);

//    @Query(value = "SELECT Pet.species FROM Pet, PotentialOwner WHERE Pet.potentialOwner.id = PotentialOwner.id")
//    String getPetSpeciesByPotentialOwnerId(Long id);

    Pet findByPotentialOwner(PotentialOwner potentialOwner);

}
