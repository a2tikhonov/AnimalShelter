package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;
import ru.skypro.jd6.team3.animalshelter.repository.NewUserMenuRepository;

import java.util.Collection;

@Service
public class NewUserMenuService{

    private final NewUserMenuRepository newUserMenuRepository;

    private final InlineKeyboardMarkup keyboard;

    private final TelegramBot telegramBot;

    public NewUserMenuService(TelegramBot telegramBot, NewUserMenuRepository newUserMenuRepository) {
        this.newUserMenuRepository = newUserMenuRepository;
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();
    }

    @Override
    public String toString() {
        return "NewUserMenuService";
    }

    public NewUserMenuButton get(Long id) {
        return newUserMenuRepository.findById(id).orElse(null);
    }

    public NewUserMenuButton get(String button) {
        return newUserMenuRepository.findByButton(button);
    }

    public Collection<NewUserMenuButton> getButtons() {
        return newUserMenuRepository.findAll();
    }

    public void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button.getButton()).callbackData(button.getButton())));
    }

    public NewUserMenuButton add(NewUserMenuButton newUserMenu) {
        return newUserMenuRepository.save(newUserMenu);
    }

    public boolean buttonExist(String button) {
        return newUserMenuRepository.existsByButton(button);
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
            NewUserMenuButton mainMenuButton = newUserMenuRepository.findByButton(query.data());
            button = mainMenuButton.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }

    public void delete(Long id) {
        newUserMenuRepository.deleteById(id);
    }
}
