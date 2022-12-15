package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cynologists")
public class Cynologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int phoneNumber;
    private String email;
    private String name;
    private double yearsOfPractice;
    private String specialties;

    public Cynologist() {
    }

    public Cynologist(Long id, long phoneNumber, String name, double yearsOfPractice, String specialties) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.yearsOfPractice = yearsOfPractice;
        this.specialties = specialties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getYearsOfPractice() {
        return yearsOfPractice;
    }

    public void setYearsOfPractice(double yearsOfPractice) {
        this.yearsOfPractice = yearsOfPractice;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cynologist that = (Cynologist) o;
        return phoneNumber == that.phoneNumber && Double.compare(that.yearsOfPractice, yearsOfPractice) == 0 && Objects.equals(id, that.id) && Objects.equals(email, that.email) && Objects.equals(name, that.name) && Objects.equals(specialties, that.specialties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, email, name, yearsOfPractice, specialties);
    }

    @Override
    public String toString() {
        return "Cynologist{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", yearsOfPractice=" + yearsOfPractice +
                ", specialties='" + specialties + '\'' +
                '}';
    }
}
