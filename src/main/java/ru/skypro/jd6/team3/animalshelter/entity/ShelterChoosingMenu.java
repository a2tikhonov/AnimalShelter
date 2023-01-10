package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "shelter_choosing_menu")
public class ShelterChoosingMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String button;

    private String callBack;

    public ShelterChoosingMenu() {

    }

    public ShelterChoosingMenu(Long id, String button, String callBack) {
        this.id = id;
        this.button = button;
        this.callBack = callBack;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    @Override
    public String toString() {
        return "ShelterChoosingMenu{" +
                "id=" + id +
                ", button='" + button + '\'' +
                ", callBack='" + callBack + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterChoosingMenu that = (ShelterChoosingMenu) o;
        return Objects.equals(id, that.id) && Objects.equals(button, that.button) && Objects.equals(callBack, that.callBack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, button, callBack);
    }
}
