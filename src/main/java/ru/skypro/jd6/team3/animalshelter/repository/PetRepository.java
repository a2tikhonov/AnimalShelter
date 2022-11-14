package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.jd6.team3.animalshelter.entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
}
