package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cynologists")
public class Cynologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phoneNumber;
    private String yearsOfPractice;
    private String specialties;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @JsonIgnore
    private Shelter shelter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getYearsOfPractice() {
        return yearsOfPractice;
    }

    public void setYearsOfPractice(String yearsOfPractice) {
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
        return Objects.equals(id, that.id) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(name, that.name) && Objects.equals(yearsOfPractice, that.yearsOfPractice) && Objects.equals(specialties, that.specialties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, name, yearsOfPractice, specialties);
    }

    @Override
    public String toString() {
        return "Cynologist{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", yearsOfPractice=" + yearsOfPractice +
                ", specialties='" + specialties + '\'' +
                '}';
    }
}
