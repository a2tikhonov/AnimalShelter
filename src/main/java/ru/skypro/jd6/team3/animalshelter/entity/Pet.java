package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;

/**
 * Класс для питомце, питомцев, а не собак так как приют может в будующем принимать и других животных
 */

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long petId;
    private String name;
    private double weight;
    private double age;
    private String breed;
    private boolean adopted = false;
    private boolean disabled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    public Pet() {}

    /**
     * Стандартные методы гет и сет
     */

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                '}';
    }


}
