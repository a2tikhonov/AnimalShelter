package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.repository.MainMenuRepository;

import java.util.Collection;


/**
 * Вызывает меню для пользователя и обрабатывает входящие запросы
 */
@Service
public class MainMenuService {

    private final InlineKeyboardMarkup keyboard;

    private final TelegramBot telegramBot;

    private final MainMenuRepository mainMenuRepository;

    private final NewUserMenuService newUserMenuService;



    public MainMenuService(TelegramBot telegramBot, MainMenuRepository mainMenuRepository, NewUserMenuService newUserMenuService) {
        this.mainMenuRepository = mainMenuRepository;
        this.newUserMenuService = newUserMenuService;
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();
    }

    public MainMenuButton get(Long id) {
        return mainMenuRepository.findById(id).orElse(null);
    }

    public MainMenuButton findByButton(String button) {
        return mainMenuRepository.findByButton(button);
    }

    public Collection<String> getButtons() {
        return mainMenuRepository.getAllButtons();
    }

    private void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button).callbackData(button)));
    }

    public MainMenuButton add(MainMenuButton mainMenuButton) {
        return mainMenuRepository.save(mainMenuButton);
    }

    public boolean buttonExist(String button) {
        return mainMenuRepository.findByButton(button) != null;
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
            MainMenuButton mainMenuButton = mainMenuRepository.findByButton(query.data());
            button = mainMenuButton.getButton();
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
        }
        return button;
    }

}
