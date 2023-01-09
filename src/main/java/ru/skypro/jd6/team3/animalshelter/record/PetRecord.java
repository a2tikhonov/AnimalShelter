package ru.skypro.jd6.team3.animalshelter.record;

public class PetRecord {
    private long id;
    private String name;
    private double weight;
    private double age;
    private String breed;
    private OwnerRecord owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public OwnerRecord getOwner() {
        return owner;
    }

    public void setOwner(OwnerRecord owner) {
        this.owner = owner;
    }
}
