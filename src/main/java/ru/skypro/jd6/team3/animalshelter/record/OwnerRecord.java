package ru.skypro.jd6.team3.animalshelter.record;

import ru.skypro.jd6.team3.animalshelter.entity.Pet;

import java.util.Collection;

/**
 * Рекорд класс Хозяин для сущности Хозяин
 */
public class OwnerRecord {

    private Long id;
    private String number;
    private String name;
    private String email;
    private Collection<PetRecord> petsRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<PetRecord> getPetsRecord() {
        return petsRecord;
    }

    public void setPetsRecord(Collection<PetRecord> petsRecord) {
        this.petsRecord = petsRecord;
    }
}
