package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonCat;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuCatRepository;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuRepository;

import java.util.Collection;

@Service
public class MainMenuCatService extends MainMenuService{

    private final InlineKeyboardMarkup keyboard;

    private final MainMenuCatRepository mainMenuCatRepository;


    public MainMenuCatService(TelegramBot telegramBot, MainMenuRepository mainMenuRepository,
                              MainMenuCatRepository mainMenuCatRepository) {
        super(telegramBot, mainMenuRepository);
        this.mainMenuCatRepository = mainMenuCatRepository;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();
    }

    @Override
    public String toString() {
        return "MainMenuCats";
    }

    @Override
    public MainMenuButton get(Long id) {
        return mainMenuCatRepository.findById(id).orElse(null);
    }


    public Collection<MainMenuButton> getButtonsCats() {
        return mainMenuCatRepository.findAll();
    }

    public MainMenuButton add(MainMenuButton mainMenuButton) {
        return mainMenuCatRepository.save(mainMenuButton);
    }

    public boolean buttonExist(String button) {
        return mainMenuCatRepository.existsByButton(button);
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
            MainMenuButton mainMenuButton = mainMenuCatRepository.findByButton(query.data());
            button = mainMenuButton.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }
}
