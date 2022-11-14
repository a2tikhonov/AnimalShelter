package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer {
    @Id
    @GeneratedValue
    private long volunteerId;

    private long phoneNumber;
    private String name;
    private String email;

    public long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Volunteer volunteer = (Volunteer) o;
        return volunteerId == volunteer.volunteerId && phoneNumber == volunteer.phoneNumber && name.equals(volunteer.name) && email.equals(volunteer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(volunteerId, phoneNumber, name, email);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "volunteerId=" + volunteerId +
                ", phoneNumber=" + phoneNumber +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
