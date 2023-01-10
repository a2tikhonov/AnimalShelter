package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.model.CallbackQuery;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButtonDog;

import java.util.Collection;


/**
 * Вызывает меню для пользователя и обрабатывает входящие запросы
 */
public interface MainMenuService {

//    InlineKeyboardMarkup keyboard = null;
//
//    TelegramBot telegramBot = null;
//
//    MainMenuRepository mainMenuRepository = null;
//
//    public MainMenuService(TelegramBot telegramBot, MainMenuRepository mainMenuRepository) {
//        this.mainMenuRepository = mainMenuRepository;
//        this.telegramBot = telegramBot;
//        this.keyboard = new InlineKeyboardMarkup();
//        setButtons();
//    }

    @Override
    public String toString();

    public void setButtons();

    public boolean buttonExist(String button);

    /**
     * Отсылает меню в чат пользователя
     *
     * @param chatId идентификатор чата
     * @param what   сообщение
     */
    public void send(Long chatId, String what);

    /**
     * Обработчик запросов от пользователя
     *
     * @param query принимает запрос с данными от кнопки, кот. нажал пользователь
     * @return answer возвращает имя нажатой кнопки для идентификации, либо строку "неверный запрос"
     */

    public String buttonTap(CallbackQuery query);

}