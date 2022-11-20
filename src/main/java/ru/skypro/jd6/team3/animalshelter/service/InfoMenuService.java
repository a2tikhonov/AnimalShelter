package ru.skypro.jd6.team3.animalshelter.service;

import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.InfoMenu;
import ru.skypro.jd6.team3.animalshelter.repository.InfoMenuRepository;

import java.util.Collection;


/**
 * Вызывает меню для пользователя и обрабатывает входящие запросы
 */
@Service
public class InfoMenuService {


    private final InfoMenuRepository infoMenuRepository;

    public InfoMenuService(InfoMenuRepository infoMenuRepository) {
        this.infoMenuRepository = infoMenuRepository;
    }


    public InfoMenu get(Long id) {
        return infoMenuRepository.findById(id).orElse(null);
    }

    public InfoMenu findByButton(String button) {
        return infoMenuRepository.findByButton(button);
    }

    public Collection<String> getButtons() {
        return infoMenuRepository.getAllButtons();
    }

    public InfoMenu add(InfoMenu infoMenu) {
        return infoMenuRepository.save(infoMenu);
    }

    public boolean buttonExist(String button) {
        return infoMenuRepository.findByButton(button) != null;
    }

}
