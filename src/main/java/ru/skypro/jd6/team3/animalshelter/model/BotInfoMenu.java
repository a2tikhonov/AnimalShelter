package ru.skypro.jd6.team3.animalshelter.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static ru.skypro.jd6.team3.animalshelter.model.BotInfoMenuConstants.*;

/**
 * Вызывает меню для пользователя и обрабатывает входящие запросы
 */
@Component
public class BotInfoMenu {


    private final InlineKeyboardMarkup keyboard;
    private final TelegramBot telegramBot;
    private ArrayList<String> buttons;

    public BotInfoMenu(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        setButtons();

    }

    private void setButtons() {
        this.buttons = new ArrayList<>(List.of(button1, button2, button3, button4));
        buttons.forEach(button -> {
            keyboard.addRow(new InlineKeyboardButton(button).callbackData(button));
        });
    }

    /**
     * Отсылает меню в чат пользователя
     * @param chatId идентификатор чата
     * @param what сообщение
     */
    public void send(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what).replyMarkup(keyboard);
        telegramBot.execute(sm);
    }

    /**
     * Обработчик запросов от пользователя
     * @param query принимает запрос с данными от кнопки, кот. нажал пользователь
     * @return answer возвращает имя нажатой кнопки для идентификации, либо строку "пустой запрос"
     */

    public String processRequest(CallbackQuery query) {
        String answer = "Пустой запрос";
        if (query != null && buttons.contains(query.data())) {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
            switch (query.data()) {
                case ("Узнать информацию о приюте"):
                    answer = BotInfoMenuConstants.SHELTER_INFO;
                    break;
                case ("Как взять собаку из приюта"):
                    answer = BotInfoMenuConstants.HOT_TO_ADOPT;
                    break;
                case ("Прислать отчет о питомце"):
                    answer = BotInfoMenuConstants.SUBMIT_PET_INFORMATION;
                    break;
                case ("Позвать волонтера"):
                    answer = BotInfoMenuConstants.ASK_FOR_VOLUNTEER;
                    break;
            }
            SendMessage sm = new SendMessage(query.message().chat().id(), answer);
            telegramBot.execute(sm);
        }
        return answer;
    }

}
