package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonDog;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuDogRepository;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuRepository;

import java.util.Collection;

@Service
public class MainMenuDogService extends MainMenuService{

    private final InlineKeyboardMarkup keyboard;

    private final MainMenuDogRepository mainMenuDogRepository;


    public MainMenuDogService(TelegramBot telegramBot, MainMenuRepository mainMenuRepository,
                              MainMenuDogRepository mainMenuDogRepository) {
        super(telegramBot, mainMenuRepository);
        this.mainMenuDogRepository = mainMenuDogRepository;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();
    }

    @Override
    public String toString() {
        return "MainMenuDogs";
    }

    @Override
    public MainMenuButton get(Long id) {
        return mainMenuDogRepository.findById(id).orElse(null);
    }

    @Override
    public Collection<MainMenuButton> getButtons() {
        return mainMenuDogRepository.findAll();
    }

    public MainMenuButtonDog add(MainMenuButtonDog mainMenuButtonDog) {
        return mainMenuDogRepository.save(mainMenuButtonDog);
    }вй

    public boolean buttonExist(String button) {
        return mainMenuDogRepository.existsByButton(button);
    }

    /**
     * Обработчик запросов от пользователя
     *
     * @param query принимает запрос с данными от кнопки, кот. нажал пользователь
     * @return answer возвращает имя нажатой кнопки для идентификации, либо строку "неверный запрос"
     */

    @Override
    public String buttonTap(CallbackQuery query) {
        String button = "";
        if (query != null && buttonExist(query.data())) {
            MainMenuButton mainMenuButton = mainMenuDogRepository.findByButton(query.data());
            button = mainMenuButton.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }
}
