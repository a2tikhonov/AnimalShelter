package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.skypro.jd6.team3.animalshelter.model.TelegramBotMenu;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;


    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    private static final Pattern pattern = Pattern.compile("([0-9.:\\s]{16})(\\s)([\\W+|\\w]+)");

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        //вынести меню в таблицу БД "startupMenu"
        TelegramBotMenu menu = new TelegramBotMenu(telegramBot, new ArrayList<>(List.of(
                "Узнать информацию о приюте"
                , "Как взять собаку из приюта"
                , "Прислать отчет о питомце"
                , "Позвать волонтера")));
    updates.forEach(update -> {
        if (update.message() != null && update.message().text().equalsIgnoreCase("/start") ) {
            //greetMessage(update);
            menu.send(update.message().chat().id(), "Добро пожаловать в приют для собак.");
        }
        menu.processRequest(update.callbackQuery());

    });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void greetMessage(Update update) {
        SendMessage responseMessage = new SendMessage(update.message().chat().id(), "Sup, @" + update.message().chat().username() + "\n What do you want?!");
        telegramBot.execute(responseMessage);
    }

    public void sendMessage(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what);
        telegramBot.execute(sm);
    }

}
