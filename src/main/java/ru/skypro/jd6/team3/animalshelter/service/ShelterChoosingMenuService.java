package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.ShelterChoosingMenu;
import ru.skypro.jd6.team3.animalshelter.repository.ShelterChoosingMenuRepository;

import java.util.Collection;

@Service
public class ShelterChoosingMenuService implements MainMenuService{

    private final InlineKeyboardMarkup keyboard;

    private final ShelterChoosingMenuRepository shelterChoosingMenuRepository;

    private final TelegramBot telegramBot;

    public ShelterChoosingMenuService(ShelterChoosingMenuRepository shelterChoosingMenuRepository,
                                      TelegramBot telegramBot) {
        this.keyboard = new InlineKeyboardMarkup();
        this.shelterChoosingMenuRepository = shelterChoosingMenuRepository;
        this.telegramBot = telegramBot;
    }

    @Override
    public String toString() {
        return "ShelterChoosingMenu";
    }

    public ShelterChoosingMenu get(Long id) {
        return shelterChoosingMenuRepository.findById(id).orElse(null);
    }

    public Collection<ShelterChoosingMenu> getButtons() {
    return shelterChoosingMenuRepository.findAll();
    }

    @Override
    public void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button.getButton()).callbackData(button.getButton())));
    }

    public ShelterChoosingMenu add (ShelterChoosingMenu shelterChoosingMenu) {
        return shelterChoosingMenuRepository.save(shelterChoosingMenu);
    }

    @Override
    public boolean buttonExist(String button) {
        return shelterChoosingMenuRepository.existsByButton(button);
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
            ShelterChoosingMenu shelterChoosingMenu = shelterChoosingMenuRepository.findByButton(query.data());
            button = shelterChoosingMenu.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }
}
