package ru.skypro.jd6.team3.animalshelter.model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.ArrayList;

public class TelegramBotMenu {

    private final InlineKeyboardMarkup keyboard;
    private final TelegramBot telegramBot;

    private final ArrayList<String> buttons;

    public TelegramBotMenu(TelegramBot telegramBot, ArrayList<String> buttons) {
        this.telegramBot = telegramBot;
        this.keyboard = new InlineKeyboardMarkup();
        this.buttons = buttons;
        buttons.forEach(button -> {
            keyboard.addRow(new InlineKeyboardButton(button).callbackData(button));
        });
    }

    public void send(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what).replyMarkup(keyboard);
        telegramBot.execute(sm);
    }

    public void processRequest(CallbackQuery query) {
        if (query != null &&  buttons.contains(query.data())) {
            AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(query.id()).text("");
            telegramBot.execute(answerCallbackQuery);
            String answer = query.data(); //надо все ответы с меню выгружать в отдельную таблицу и вытягивать ответ из БД в этой строке
            SendMessage sm = new SendMessage(query.message().chat().id(), answer);
            telegramBot.execute(sm);
        }
    }

}
