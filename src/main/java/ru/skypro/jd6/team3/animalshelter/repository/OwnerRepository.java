package ru.skypro.jd6.team3.animalshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.jd6.team3.animalshelter.entity.Owner;

/**
 * Репозиторий для Хозяина
 */
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner getOwnersByPhoneNumber(String phoneNumber);

    Owner getOwnersByEmail(String email);
}
