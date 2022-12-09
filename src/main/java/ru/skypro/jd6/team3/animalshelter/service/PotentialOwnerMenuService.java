package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.PotentialOwnerMenuButton;
import ru.skypro.jd6.team3.animalshelter.repository.PotentialOwnerMenuRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class PotentialOwnerMenuService {

    private final InlineKeyboardMarkup keyboard;

    private final InlineKeyboardMarkup keyboard2;

    private final TelegramBot telegramBot;

    private final PotentialOwnerMenuRepository potentialOwnerMenuRepository;

    public PotentialOwnerMenuService(TelegramBot telegramBot, PotentialOwnerMenuRepository potentialOwnerMenuRepository) {
        this.potentialOwnerMenuRepository = potentialOwnerMenuRepository;
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        this.keyboard2 = new InlineKeyboardMarkup();
        setButtons();
    }

    @Override
    public String toString() {
        return "PotentialOwnerMenuService";
    }

    public PotentialOwnerMenuButton get(Long id) {
        return potentialOwnerMenuRepository.findById(id).orElse(null);
    }

    public PotentialOwnerMenuButton get(String button) {
        return potentialOwnerMenuRepository.findByButton(button);
    }

    public Collection<PotentialOwnerMenuButton> getButtons() {
        return potentialOwnerMenuRepository.findAll();
    }

    private void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button.getButton()).callbackData(button.getCallBack())));
    }

    public PotentialOwnerMenuButton add(PotentialOwnerMenuButton potentialOwnerMenuButton) {
        return potentialOwnerMenuRepository.save(potentialOwnerMenuButton);
    }

    public boolean buttonExist(String button) {
        return potentialOwnerMenuRepository.existsByButton(button);
    }

    /**
     * Отсылает меню в чат пользователя
     *
     * @param chatId идентификатор чата
     * @param what   сообщение
     */
    public void send(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what).replyMarkup(keyboard);
        telegramBot.execute(sm);
    }

    /**
     * Обработчик запросов от пользователя
     *
     * @param query принимает запрос с данными от кнопки, кот. нажал пользователь
     * @return answer возвращает имя нажатой кнопки для идентификации, либо строку "неверный запрос"
     */

    public String buttonTap(CallbackQuery query) {
        String button = "";
        if (query != null && buttonExist(query.data())) {
            PotentialOwnerMenuButton potentialOwnerMenuButton = potentialOwnerMenuRepository.findByButton(query.data());
            button = potentialOwnerMenuButton.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }
}
