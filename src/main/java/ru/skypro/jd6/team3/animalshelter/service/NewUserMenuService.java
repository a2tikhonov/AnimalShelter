package ru.skypro.jd6.team3.animalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.entity.MainMenuButton;
import ru.skypro.jd6.team3.animalshelter.entity.NewUserMenuButton;
import ru.skypro.jd6.team3.animalshelter.repository.NewUserMenuRepository;

import java.util.Collection;

@Service
public class NewUserMenuService{

    //private final PotentialOwnerService potentialOwnerService;

    private final NewUserMenuRepository newUserMenuRepository;

    private final InlineKeyboardMarkup keyboard;

    private final TelegramBot telegramBot;

    public NewUserMenuService(NewUserMenuRepository newUserMenuRepository, TelegramBot telegramBot) {
        this.newUserMenuRepository = newUserMenuRepository;
        this.keyboard = new InlineKeyboardMarkup();
        this.telegramBot = telegramBot;
        setButtons();
    }

    public NewUserMenuButton get(Long id) {
        return newUserMenuRepository.findById(id).orElse(null);
    }

    public Collection<String> getButtons() {
        return newUserMenuRepository.getAllButtons();
    }

    public void setButtons() {
        getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button).callbackData(button)));
    }

    public NewUserMenuButton add(NewUserMenuButton newUserMenu) {
        return newUserMenuRepository.save(newUserMenu);
    }

    public boolean buttonExist(String button) {
        return newUserMenuRepository.findByButton(button) != null;
    }

    public MainMenuButton findByButton(String button) {
        return newUserMenuRepository.findByButton(button);
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

    public String processRequest(CallbackQuery query) {
        String answer = "Неверный запрос";
        if (query != null && buttonExist(query.data())) {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
            SendMessage sm;
            if (!query.data().equals("Записать данные для связи")) {
                answer = findByButton(query.data()).getCallBack();

            } else {
                answer = "Пожалуйста, введите Ваши контактные данные";
            }
            sm = new SendMessage(query.message().chat().id(), answer);
            telegramBot.execute(sm);
        }
        return answer;
    }

}
