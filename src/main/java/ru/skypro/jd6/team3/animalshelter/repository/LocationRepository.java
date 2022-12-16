package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {

}
