package ru.skypro.jd6.team3.animalshelter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "main_menu_cats")
public class MainMenuButtonCat extends MainMenuButton{

    public MainMenuButtonCat() {
    }

    public MainMenuButtonCat(Long id, String button, String callBack) {
        super(id, button, callBack);
    }

}
