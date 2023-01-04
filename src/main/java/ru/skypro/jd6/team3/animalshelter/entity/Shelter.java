package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue
    private Long id;
    private String location_info;
    private String description;
    private String email;
    private String howToFindUs;

    @OneToMany(mappedBy = "shelter")
//    @JoinColumn(name = "volunteer_id")
    @JsonIgnore
    @Nullable
    private Collection<Volunteer> volunteers;

    @OneToMany(mappedBy = "shelter")
//    @JoinColumn(name = "pet_id")
    @JsonIgnore
    @Nullable
    private Collection<Pet> pets;

    @OneToMany(mappedBy = "shelter")
//    @JoinColumn(name = "cynologist_id")
    @JsonIgnore
    @Nullable
    private Collection<Cynologist> cynologists;

    @OneToOne
    @JoinColumn(name = "location_id")
    @JsonIgnore
    @Nullable
    private Location location;

    @OneToOne
    @JoinColumn(name = "recommendations_id")
    @JsonIgnore
    @Nullable
    private Recommendations recommendations;

//    public Shelter() {
//        this.id = null;
//        this.location_info = null;
//        this.description = null;
//        this.email = null;
//        this.howToFindUs = null;
//        this.volunteer = null;
//        this.pet = null;
//        this.location = null;
//    }

    public Shelter() {
    }

    ;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return id == shelter.id && location_info.equals(shelter.location_info) && description.equals(shelter.description) && email.equals(shelter.email) && howToFindUs.equals(shelter.howToFindUs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location_info, description, email, howToFindUs);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation_info() {
        return location_info;
    }

    public void setLocation_info(String location_info) {
        this.location_info = location_info;
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

    public String getHowToFindUs() {
        return howToFindUs;
    }

    public void setHowToFindUs(String howToFindUs) {
        this.howToFindUs = howToFindUs;
    }

    public Collection<Volunteer> getVolunteers() {
        return volunteers;
    }

    public void setVolunteers(Collection<Volunteer> volunteers) {
        this.volunteers = volunteers;
    }

    public Collection<Pet> getPets() {
        return pets;
    }

    public void setPets(Collection<Pet> pets) {
        this.pets = pets;
    }

    public Collection<Cynologist> getCynologists() {
        return cynologists;
    }


    public void setCynologists(Collection<Cynologist> cynologists) {
        this.cynologists = cynologists;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Recommendations getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Recommendations recommendations) {
        this.recommendations = recommendations;
    }

}

