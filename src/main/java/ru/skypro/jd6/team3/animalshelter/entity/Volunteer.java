package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    private Long id;

    private String name;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "potential_owner_id")
    @Nullable
    @JsonIgnore
    private PotentialOwner potentialOwner;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    @Nullable
    @JsonIgnore
    private Shelter shelter;

    public Volunteer() {
    }

    public Volunteer(Long id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return id.equals(volunteer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public PotentialOwner getPotentialOwner() {
        return potentialOwner;
    }

    public void setPotentialOwner(@Nullable PotentialOwner potentialOwner) {
        this.potentialOwner = potentialOwner;
    }

    @Nullable
    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(@Nullable Shelter shelter) {
        this.shelter = shelter;
    }
}