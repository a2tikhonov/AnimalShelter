package ru.skypro.jd6.team3.animalshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
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

    // Вводим для дальнейшего логирования функционала бота
    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    // Инициализация бота
    private final TelegramBot telegramBot;

    // Конструктор
    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    // Вводим регулярное выражение. Если входящая строка не соответствует перечню символов, то она не обрабатывается
    private static final Pattern pattern = Pattern.compile("([0-9.:\\s]{16})(\\s)([\\W+|\\w]+)");

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        // Вынести меню в таблицу БД "startupMenu" (создаем меню, которое бот отправляет пользователю)
        TelegramBotMenu menu = new TelegramBotMenu(telegramBot, new ArrayList<>(List.of(
                TelegramBotStringConstants.SHELTER_INFO
                , TelegramBotStringConstants.HOT_TO_ADOPT
                , TelegramBotStringConstants.SUBMIT_PET_INFORMATION
                , TelegramBotStringConstants.ASK_FOR_VOLUNTEER)));
        updates.forEach(update -> {
            if (update.message() != null && update.message().text().equalsIgnoreCase("/start")) {
                // Отправляем меню в диалог с пользователем
                menu.send(update.message().chat().id(), TelegramBotStringConstants.GREET_MESSAGE);
            }
            menu.processRequest(update.callbackQuery());

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    //Вынесли приветственное сообщение от бота в отдельный метод
    private void greetMessage(Update update) {
        SendMessage responseMessage = new SendMessage(update.message().chat().id(), "Sup, @" + update.message().chat().username() + "\n What do you want?!");
        telegramBot.execute(responseMessage);
    }

    // Вынесли отправку сообщения ботом в отдельный метод
    public void sendMessage(Long chatId, String what) {
        SendMessage sm = new SendMessage(chatId, what);
        telegramBot.execute(sm);
    }

}
