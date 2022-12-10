package ru.skypro.jd6.team3.animalshelter.entity;

import org.hibernate.annotations.Cascade;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double age;

    @Column(nullable = false)
    private String breed;

    @Column(nullable = false)
    private String species;

    private boolean disabled = false;

    @OneToOne(fetch = FetchType.LAZY)
    private PotentialOwner potentialOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Shelter shelter;
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

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public PotentialOwner getPotentialOwner() {
        return potentialOwner;
    }

    public void setPotentialOwner(PotentialOwner potentialOwner) {
        this.potentialOwner = potentialOwner;
    }


    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
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
