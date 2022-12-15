package ru.skypro.jd6.team3.animalshelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "potential_owners")
public class PotentialOwner {
    @Id
    private Long id;

    private String name;

    private String phone;

    private String locationInMenu;

    public PotentialOwner() {
    }

    public PotentialOwner(Long id, String name, String phone, String locationInMenu) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.locationInMenu = locationInMenu;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotentialOwner that = (PotentialOwner) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PotentialOwner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
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

    public String getLocationInMenu() {
        return locationInMenu;
    }

    public void setLocationInMenu(String locationInMenu) {
        this.locationInMenu = locationInMenu;
    }

}