package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;
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
    private String howToFindUs;
    @ManyToOne
    private Volunteer volunteer;
    @ManyToOne
    private Pet pet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return shelterId == shelter.shelterId && location.equals(shelter.location) && description.equals(shelter.description) && email.equals(shelter.email) && howToFindUs.equals(shelter.howToFindUs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelterId, location, description, email, howToFindUs);
    }

    public String getHowToFindUs() {
        return howToFindUs;
    }

    public void setHowToFindUs(String howToFindUs) {
        this.howToFindUs = howToFindUs;
    }

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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
