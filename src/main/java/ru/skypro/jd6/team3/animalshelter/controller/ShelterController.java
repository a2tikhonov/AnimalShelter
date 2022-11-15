package ru.skypro.jd6.team3.animalshelter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.jd6.team3.animalshelter.service.ShelterService;

@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * Получить местоположение приюта
     *
     * @ "/shelter-location" - обращение через браузер
     */
    @GetMapping("/shelter-location")
    public String getShelterLocation() {
        return shelterService.getShelterLocation();
    }

    /**
     * Получить информацию о приюте
     *
     * @ "/shelter-description" - обращение через браузер
     */
    @GetMapping("/shelter-description")
    public String getShelterDescription() {
        return shelterService.getShelterDescription();
    }

    /**
     * Получить адрес электронной почты приюта
     *
     * @ "/shelter-email" - обращение через браузер
     */
    @GetMapping("/shelter-email")
    public String getShelterEmail() {
        return shelterService.getShelterEmail();
    }
}
