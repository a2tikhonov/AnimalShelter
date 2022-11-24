package ru.skypro.jd6.team3.animalshelter.record;

/**
 * Рекорд класс Хозяин для сущности Хозяин
 */
public class OwnerRecord {

    private Long id;
    private String number;
    private String name;
    private String email;
    private PetRecord pet;

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

    public PetRecord getPet() {
        return pet;
    }

    public void setPet(PetRecord pet) {
        this.pet = pet;
    }
}
