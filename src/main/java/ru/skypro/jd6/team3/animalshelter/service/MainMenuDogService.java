package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonDog;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuDogRepository;
import java.util.Collection;

@Service
public class MainMenuDogService implements MainMenuService {

    private final InlineKeyboardMarkup keyboard;

    private final MainMenuDogRepository mainMenuDogRepository;

    private final TelegramBot telegramBot;

    public MainMenuDogService(TelegramBot telegramBot,
                              MainMenuDogRepository mainMenuDogRepository) {
        this.mainMenuDogRepository = mainMenuDogRepository;
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();
    }

    @Override
    public String toString() {
        return "MainMenuCats";
    }

    public MainMenuButtonDog get(Long id) {
        return mainMenuDogRepository.findById(id).orElse(null);
    }

    public Collection<MainMenuButtonDog> getButtons() {
        return mainMenuDogRepository.findAll();
    }

    @Override
    public void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button.getButton()).callbackData(button.getButton())));
    }

    public MainMenuButtonDog add(MainMenuButtonDog mainMenuButtonDogCat) {
        return mainMenuDogRepository.save(mainMenuButtonDogCat);
    }

    @Override
    public boolean buttonExist(String button) {
        return mainMenuDogRepository.existsByButton(button);
    }

    @Override
    public void send(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what).replyMarkup(keyboard);
        telegramBot.execute(sm);
    }

    @Override
    public String buttonTap(CallbackQuery query) {
        String button = "";
        if (query != null && buttonExist(query.data())) {
            MainMenuButtonDog mainMenuButtonDog = mainMenuDogRepository.findByButton(query.data());
            button = mainMenuButtonDog.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }
}
