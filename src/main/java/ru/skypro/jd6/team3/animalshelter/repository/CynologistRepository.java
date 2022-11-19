package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Cynologist;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;

@Repository
public interface CynologistRepository  extends JpaRepository<Cynologist,Long> {
}
