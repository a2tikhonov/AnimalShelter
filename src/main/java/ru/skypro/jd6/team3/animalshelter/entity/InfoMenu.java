package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "infomenu")
public class InfoMenu {

    @Id
    @GeneratedValue(generator = "infoMenuGen")
    private Long id;

    private String button;

    private String callBack;

    public InfoMenu() {
    }

    public InfoMenu(Long id, String button, String callBack) {
        this.id = id;
        this.button = button;
        this.callBack = callBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoMenu infoMenu = (InfoMenu) o;
        return id.equals(infoMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Menu{" +
                "button='" + button + '\'' +
                ", callBack='" + callBack + '\'' +
                '}';
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
}
