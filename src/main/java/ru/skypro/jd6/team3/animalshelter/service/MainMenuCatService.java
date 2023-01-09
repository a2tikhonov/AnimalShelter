package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonCat;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuCatRepository;

import java.util.Collection;

@Service
public class MainMenuCatService implements MainMenuService{

    private final InlineKeyboardMarkup keyboard;

    private final MainMenuCatRepository mainMenuCatRepository;

    private final TelegramBot telegramBot;

    public MainMenuCatService(TelegramBot telegramBot,
                              MainMenuCatRepository mainMenuCatRepository) {
        this.mainMenuCatRepository = mainMenuCatRepository;
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();
    }

    @Override
    public String toString() {
        return "MainMenuCats";
    }

    public MainMenuButtonCat get(Long id) {
        return mainMenuCatRepository.findById(id).orElse(null);
    }

    public Collection<MainMenuButtonCat> getButtons() {
        return mainMenuCatRepository.findAll();
    }

    @Override
    public void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button.getButton()).callbackData(button.getButton())));
    }

    public MainMenuButtonCat add(MainMenuButtonCat mainMenuButtonDogCat) {
        return mainMenuCatRepository.save(mainMenuButtonDogCat);
    }

    @Override
    public boolean buttonExist(String button) {
        return mainMenuCatRepository.existsByButton(button);
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
            MainMenuButtonCat mainMenuButtonCat = mainMenuCatRepository.findByButton(query.data());
            button = mainMenuButtonCat.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }
}
