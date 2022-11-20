package ru.skypro.jd6.team3.animalshelter.component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.service.InfoMenuService;


/**
 * Вызывает меню для пользователя и обрабатывает входящие запросы
 */
@Service
public class InfoMenuForBot {
    private final InlineKeyboardMarkup keyboard;
    private final TelegramBot telegramBot;
    private final InfoMenuService infoMenuService;


    public InfoMenuForBot(TelegramBot telegramBot, InfoMenuService infoMenuService) {
        this.infoMenuService = infoMenuService;
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
    }

    public void setButtons() {
        infoMenuService.getButtons().forEach(button ->
                keyboard.addRow(new InlineKeyboardButton(button).callbackData(button)));
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
        if (query != null && infoMenuService.buttonExist(query.data())) {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
            answer = infoMenuService.findByButton(query.data()).getCallBack();
            SendMessage sm = new SendMessage(query.message().chat().id(), answer);
            telegramBot.execute(sm);
        }
        return answer;
    }

}

