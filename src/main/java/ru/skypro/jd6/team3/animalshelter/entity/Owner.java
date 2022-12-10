package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

/**
 * Класс для Хозяина собак
        */
@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownerId;

    @Column(nullable = false)
    private String name;

    @Column(unique=true, nullable = false)
    private String email;
    private boolean petOwner = false;

    @Column(unique=true, nullable = false)
    private String phoneNumber;

    /**
     * Стандартные методы гет и сет
     */


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Pet> pets;

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPetOwner() {
        return petOwner;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                ", phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", pets=" + pets +
                '}';
    }
}
