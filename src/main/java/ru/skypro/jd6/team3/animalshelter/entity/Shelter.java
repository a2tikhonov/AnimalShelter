package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "shelter")
public class Shelter {

    @Id
    @GeneratedValue
    private long shelterId;
    private String location;
    private String description;
    private String email;

    public long getShelterId() {
        return shelterId;
    }

    public void setShelterId(long shelterId) {
        this.shelterId = shelterId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return shelterId == shelter.shelterId && location.equals(shelter.location) && description.equals(shelter.description) && email.equals(shelter.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelterId, location, description, email);
    }
}
